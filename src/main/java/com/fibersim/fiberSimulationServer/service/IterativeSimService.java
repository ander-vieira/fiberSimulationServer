package com.fibersim.fiberSimulationServer.service;

import com.fibersim.fiberSimulationServer.core.iterative.GeometricalParams;
import com.fibersim.fiberSimulationServer.core.iterative.SideAbsorption;
import com.fibersim.fiberSimulationServer.core.resources.DyeDopant;
import com.fibersim.fiberSimulationServer.core.resources.LambdaRange;
import com.fibersim.fiberSimulationServer.core.resources.Medium;
import com.fibersim.fiberSimulationServer.core.resources.PowerSource;
import com.fibersim.fiberSimulationServer.core.resources.factory.DyeDopantFactory;
import com.fibersim.fiberSimulationServer.core.util.Constants;
import com.fibersim.fiberSimulationServer.core.util.SimulationTimer;
import com.fibersim.fiberSimulationServer.dto.IterativeSimParamsDTO;
import com.fibersim.fiberSimulationServer.dto.IterativeSimResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class IterativeSimService {
    @Autowired
    SideAbsorption sideAbsorption;
    @Autowired
    GeometricalParams geometricalParams;
    @Autowired
    SimulationTimer simulationTimer;
    @Autowired
    DyeDopantFactory dyeDopantFactory;

    public IterativeSimResponseDTO dyeIterative(IterativeSimParamsDTO params) {
        simulationTimer.startTimer();

        double dz = 5e-5;
        int numZZ = (int)Math.ceil(params.getLength()/dz);

        DyeDopant dopant = dyeDopantFactory.make("Rh6G");

        int numLL = 151;
        LambdaRange lambdaRange = new LambdaRange();
        lambdaRange.addDopant(dopant);
        double[] ll = lambdaRange.getLL(numLL);
        double dlambda = lambdaRange.getDlambda(numLL);

        double[] sigmaabs = dopant.sigmaabs.getArray(ll);
        double[] sigmaemi = dopant.sigmaemi.getArray(ll);
        double sumEmi = 0;
        for(double sigma: sigmaemi) sumEmi += sigma;

        PowerSource sun = new PowerSource("AM1.5");
        double[] Isol = sun.I.getArray(ll);

        Medium pmma = new Medium("PMMA");
        double[] nPMMA = pmma.refractionIndex.getArray(ll);
        double[] alfaPMMA = pmma.attenuation.getArray(ll);

        Medium clad = new Medium("clad");
        double[] nClad = clad.refractionIndex.getArray(ll);
        double[] alfaClad = clad.attenuation.getArray(ll);

        double[] beta = geometricalParams.betaB(nPMMA);
        double[] Kz = geometricalParams.KzB(nPMMA);

        double[] alphaCore = new double[numLL];
        double[] alphaDopant = new double[numLL];
        for(int k = 0 ; k < numLL ; k++) {
            alphaDopant[k] = params.getConcentration()*sigmaabs[k];
            alphaCore[k] = alfaPMMA[k]+alphaDopant[k];
        }

        double[] sideEfficiency = sideAbsorption.twoInterphases(params.getDiameter(), 0.98, nPMMA, alphaCore, alphaDopant, nClad, alfaClad);

        double Nsolconst = 0;
        double[] Nabsconst = new double[numLL];
        double[] Nestconst = new double[numLL];
        double[] Pattconst = new double[numLL];
        double[] PNconst1 = new double[numLL];
        double[] PNconst2 = new double[numLL];
        for(int k = 0 ; k < numLL ; k++) {
            double concentrationToPower = Math.PI* Constants.h*Constants.c* params.getDiameter()* params.getDiameter()/(4*ll[k]);

            Nsolconst += params.getDiameter()*Isol[k]*sideEfficiency[k]*dlambda/concentrationToPower;
            Nabsconst[k] = Kz[k]*sigmaabs[k]/concentrationToPower;
            Nestconst[k] = Kz[k]*sigmaemi[k]/concentrationToPower;
            Pattconst[k] = Kz[k]*(alfaPMMA[k]+ params.getConcentration()*sigmaabs[k])*dz;
            PNconst1[k] = concentrationToPower*beta[k]*sigmaemi[k]/sumEmi*dz/dopant.tauRad;
            PNconst2[k] = Kz[k]*(sigmaabs[k]+sigmaemi[k])*dz;
        }

        double[][] P = new double[numZZ][numLL];
        double[][] Pleft = new double[numZZ][numLL];
        double[] N2 = new double[numZZ-1];

        double error;
        do {
            for(int k = 0 ; k < numLL ; k++) {
                P[0][k] = 0;
                Pleft[numZZ-1][k] = 0;
            }

            double[] previousP = P[numZZ-1].clone();

            //Update N2
            for(int j = 0 ; j < numZZ-1 ; j++) {
                double wabs = 0, west = 0;
                for(int k = 0 ; k < numLL ; k++) {
                    double evalP = (P[j][k]+P[j+1][k]+Pleft[j][k]+Pleft[j+1][k])/2;

                    wabs += Nabsconst[k]*evalP;
                    west += Nestconst[k]*evalP;
                }

                double A = 1/dopant.tauRad+1/dopant.tauNR+wabs+west;
                double b = Nsolconst+ params.getConcentration()*wabs;

                N2[j] = b/A;
            }

            //Update P
            for(int j = 0 ; j < numZZ-1 ; j++) {
                double evalN2 = N2[j];

                for(int k = 0 ; k < numLL ; k++) {
                    double evalP = P[j][k];

                    P[j+1][k] = evalP;
                    P[j+1][k] -= Pattconst[k]*evalP;
                    P[j+1][k] += PNconst1[k]*evalN2;
                    P[j+1][k] += PNconst2[k]*evalN2*evalP;
                }
            }

            //Update Pleft
            for(int j = numZZ-1 ; j > 0 ; j--) {
                double evalN2 = N2[j-1];

                for(int k = 0 ; k < numLL ; k++) {
                    double evalP = Pleft[j][k];

                    Pleft[j-1][k] = evalP;
                    Pleft[j-1][k] -= Pattconst[k]*evalP;
                    Pleft[j-1][k] += PNconst1[k]*evalN2*evalP;
                    Pleft[j-1][k] += PNconst2[k]*evalN2*evalP;
                }
            }

            error = 0;
            for(int k = 0 ; k < numLL ; k++) {
                double e = Math.abs(P[numZZ-1][k]-previousP[k])/(P[numZZ-1][k]+Double.MIN_VALUE);

                if(e > error) error = e;
            }
        } while(error > 1e-8);

        return IterativeSimResponseDTO.builder()
                .elapsedTime(simulationTimer.getTime())
                .lightP(Arrays.stream(P[numZZ-1]).sum())
                .build();
    }
}
