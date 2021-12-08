package com.fibersim.fiberSimulationServer.service;

import com.fibersim.fiberSimulationServer.core.iterative.GeometricalValues;
import com.fibersim.fiberSimulationServer.core.iterative.IterativeAttenuator;
import com.fibersim.fiberSimulationServer.core.iterative.SideAbsorption;
import com.fibersim.fiberSimulationServer.core.util.Constants;
import com.fibersim.fiberSimulationServer.core.util.LambdaFunction;
import com.fibersim.fiberSimulationServer.core.util.LambdaRange;
import com.fibersim.fiberSimulationServer.core.util.SimulationTimer;
import com.fibersim.fiberSimulationServer.dto.DyeDopantDTO;
import com.fibersim.fiberSimulationServer.dto.IterativeSimParamsDTO;
import com.fibersim.fiberSimulationServer.dto.IterativeSimResponseDTO;
import com.fibersim.fiberSimulationServer.resources.reader.DyeDopantReader;
import com.fibersim.fiberSimulationServer.resources.reader.MediumReader;
import com.fibersim.fiberSimulationServer.resources.reader.PowerSourceReader;
import com.fibersim.fiberSimulationServer.resources.resource.MediumResource;
import com.fibersim.fiberSimulationServer.resources.resource.PowerSourceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class IterativeSimService {
    @Autowired
    SimulationTimer simulationTimer;
    @Autowired
    DyeDopantReader dyeDopantReader;
    @Autowired
    MediumReader mediumReader;
    @Autowired
    PowerSourceReader powerSourceReader;

    public IterativeSimResponseDTO dyeIterative(IterativeSimParamsDTO params) {
        simulationTimer.startTimer();

        double dz = 5e-5;
        int numZZ = (int)Math.ceil(params.getLength()/dz);

        List<DyeDopantDTO> dyeDopants = dyeDopantReader.readDopants(params.getDyeDopants());

        MediumResource pmma = mediumReader.readMedium("PMMA");
        MediumResource clad = mediumReader.readMedium("clad");

        PowerSourceResource sun = powerSourceReader.readSource("AM1.5");

        int numLL = 151;
        LambdaRange lambdaRange = new LambdaRange();
        for(DyeDopantDTO dyeDopant : dyeDopants) {
            lambdaRange.addDopant(dyeDopant.getDyeDopant());
        }
        double[] ll = lambdaRange.getLL(numLL);
        double dlambda = lambdaRange.getDLambda(numLL);

        double[] sigmaabs = dyeDopants.get(0).getDyeDopant().getSigmaabs().getArray(ll);
        double[] sigmaemi = dyeDopants.get(0).getDyeDopant().getSigmaemi().getArray(ll);
        double sumEmi = 0;
        for(double sigma: sigmaemi) sumEmi += sigma;

        LambdaFunction sideEfficiency = SideAbsorption.twoInterphases(params.getDiameter(), 0.98, dyeDopants.get(0), pmma, clad);

        IterativeAttenuator iterativeAttenuator = new IterativeAttenuator(pmma, ll, dz);

        double Nsolconst = 0;
        double[] Nabsconst = new double[numLL];
        double[] Nestconst = new double[numLL];
        double[] Pattconst = new double[numLL];
        double[] PNconst1 = new double[numLL];
        double[] PNconst2 = new double[numLL];
        for(int k = 0 ; k < numLL ; k++) {
            double concentrationToPower = Math.PI * Constants.h * Constants.c * params.getDiameter() * params.getDiameter() / (4*ll[k]);
            double nPMMA = pmma.getRefractionIndex().eval(ll[k]);
            double beta = GeometricalValues.betaB(nPMMA);
            double Kz = GeometricalValues.KzB(nPMMA);
            double Isol = sun.getIrradiance().eval(ll[k]);

            Nsolconst += params.getDiameter()*Isol*sideEfficiency.eval(ll[k])*dlambda/concentrationToPower;
            Nabsconst[k] = Kz*sigmaabs[k]/concentrationToPower;
            Nestconst[k] = Kz*sigmaemi[k]/concentrationToPower;
            Pattconst[k] = -Kz*dyeDopants.get(0).getConcentration()*sigmaabs[k]*dz;
            PNconst1[k] = concentrationToPower*beta*sigmaemi[k]/sumEmi*dz/dyeDopants.get(0).getDyeDopant().getTauRad();
            PNconst2[k] = Kz*(sigmaabs[k]+sigmaemi[k])*dz;
        }

        double[] finalP = new double[numLL];
        double[] wabs = new double[numZZ-1];
        double[] west = new double[numZZ-1];
        double[] N2 = new double[numZZ-1];
        double lambdaP, lambdaPleft, error, e, A, b, evalN2, oldLambdaP, oldLambdaPleft;

        do {

            //Update N2
            for(int j = 0 ; j < numZZ-1 ; j++) {

                A = 1/dyeDopants.get(0).getDyeDopant().getTauRad()+1/dyeDopants.get(0).getDyeDopant().getTauNR()+wabs[j]+west[j];
                b = Nsolconst + dyeDopants.get(0).getConcentration()*wabs[j];

                N2[j] = b/A;

                wabs[j] = 0;
                west[j] = 0;
            }

            double[] previousP = finalP.clone();

            for(int k = 0 ; k < numLL ; k++) {
                //Propagate P to the right
                lambdaP = 0;
                for(int j = 0 ; j < numZZ-1 ; j++) {
                    evalN2 = N2[j];
                    oldLambdaP = lambdaP;

                    //Update P
                    lambdaP += iterativeAttenuator.updateP(oldLambdaP, k);
                    lambdaP += (Pattconst[k]+PNconst2[k]*evalN2)*oldLambdaP+PNconst1[k]*evalN2;

                    //Update wabs and west
                    wabs[j] += Nabsconst[k]*(oldLambdaP+lambdaP)/2;
                    west[j] += Nestconst[k]*(oldLambdaP+lambdaP)/2;
                }

                //Propagate P to the left
                lambdaPleft = 0;
                for(int j = numZZ-1 ; j > 0 ; j--) {
                    evalN2 = N2[j-1];
                    oldLambdaPleft = lambdaPleft;

                    //Update Pleft
                    lambdaPleft += iterativeAttenuator.updateP(oldLambdaPleft, k);
                    lambdaPleft += (Pattconst[k]+PNconst2[k]*evalN2)*oldLambdaPleft+PNconst1[k]*evalN2;

                    //Update wabs and west
                    wabs[j-1] += Nabsconst[k]*(oldLambdaPleft+lambdaPleft)/2;
                    west[j-1] += Nestconst[k]*(oldLambdaPleft+lambdaPleft)/2;
                }

                finalP[k] = lambdaP;
            }

            error = 0;
            for(int k = 0 ; k < numLL ; k++) {
                e = Math.abs(finalP[k]-previousP[k])/(finalP[k]+Double.MIN_VALUE);

                if(e > error) error = e;
            }
        } while(error > 1e-8);

        return IterativeSimResponseDTO.builder()
                .elapsedTime(simulationTimer.getTime())
                .lightP(Arrays.stream(finalP).sum())
                .build();
    }
}