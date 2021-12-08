package com.fibersim.fiberSimulationServer.core.iterative;

import com.fibersim.fiberSimulationServer.core.util.Constants;

import java.util.Arrays;

public class IterativeDyeDopant {
    private final double Nsolconst;
    private final double Nespconst;
    private final double concentration;
    private final double[] Nabsconst;
    private final double[] Nestconst;
    private final double[] Pabsconst;
    private final double[] PNconst1;
    private final double[] PNconst2;

    private final double[] N2;
    private final double[] wabs;
    private final double[] west;

    public IterativeDyeDopant(IterativeDyeDopantParams params) {
        double[] ll = params.getLl();

        Nsolconst = params.getNsolconst();
        Nespconst = 1/params.getDyeDopantDTO().getDyeDopant().getTauRad()+1/params.getDyeDopantDTO().getDyeDopant().getTauNR();
        concentration = params.getDyeDopantDTO().getConcentration();

        Nabsconst = new double[ll.length];
        Nestconst = new double[ll.length];
        Pabsconst = new double[ll.length];
        PNconst1 = new double[ll.length];
        PNconst2 = new double[ll.length];

        double sumEmi = Arrays.stream(ll).map(lambda -> params.getDyeDopantDTO().getDyeDopant().getSigmaemi().eval(lambda)).sum();

        double concentrationToPower, refractionIndex, beta, Kz, sigmaabs, sigmaemi;
        for(int k = 0 ; k < ll.length ; k++) {
            concentrationToPower = Math.PI * Constants.h * Constants.c * params.getDiameter() * params.getDiameter() / (4*ll[k]);
            refractionIndex = params.getMedium().getRefractionIndex().eval(ll[k]);
            beta = GeometricalValues.betaB(refractionIndex);
            Kz = GeometricalValues.KzB(refractionIndex);
            sigmaabs = params.getDyeDopantDTO().getDyeDopant().getSigmaabs().eval(ll[k]);
            sigmaemi = params.getDyeDopantDTO().getDyeDopant().getSigmaemi().eval(ll[k]);

            Nabsconst[k] = Kz*sigmaabs/concentrationToPower;
            Nestconst[k] = Kz*sigmaemi/concentrationToPower;
            Pabsconst[k] = -Kz*concentration*sigmaabs*params.getDz();
            PNconst1[k] = concentrationToPower*beta*(sigmaemi/sumEmi)*params.getDz()/params.getDyeDopantDTO().getDyeDopant().getTauRad();
            PNconst2[k] = Kz*(sigmaabs+sigmaemi)*params.getDz();
        }

        N2 = new double[params.getNumZZ()-1];
        wabs = new double[params.getNumZZ()-1];
        west = new double[params.getNumZZ()-1];
    }

    public void updateN2() {
        double A, b;
        for(int j = 0 ; j < N2.length ; j++) {
            A = Nespconst+wabs[j]+west[j];
            b = Nsolconst+concentration*wabs[j];

            wabs[j] = 0;
            west[j] = 0;

            N2[j] = b/A;
        }
    }

    public double updateP(double oldP, int j, int k) {
        return (Pabsconst[k]+PNconst2[k]*N2[j])*oldP+PNconst1[k]*N2[j];
    }

    public void updateW(double oldP, double P, int j, int k) {
        wabs[j] += Nabsconst[k]*(oldP+P)/2;
        west[j] += Nestconst[k]*(oldP+P)/2;
    }
}
