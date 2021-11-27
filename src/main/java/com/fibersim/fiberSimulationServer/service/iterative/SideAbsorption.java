package com.fibersim.fiberSimulationServer.service.iterative;

import com.fibersim.fiberSimulationServer.service.basics.Misc;

public class SideAbsorption {
    private int k;
    private final double diameter;
    private final double q;
    private final double[] nCore;
    private final double[] alphaCore;
    private final double[] alphaDopant;
    private final double[] nClad;
    private final double[] alphaClad;

    public SideAbsorption(double diameter, double q, double[] nCore, double[] alphaCore, double[] alphaDopant, double[] nClad, double[] alphaClad) {
        this.diameter = diameter;
        this.q = q;
        this.nCore = nCore.clone();
        this.alphaCore = alphaCore.clone();
        this.alphaDopant = alphaDopant.clone();
        this.nClad = nClad.clone();
        this.alphaClad = alphaClad.clone();
        this.k = 0;
    }

    public double[] noReflections() {
        double[] result = new double[nCore.length];
        UnitIntegral integrator = new UnitIntegral() {
            @Override
            public double integrand(double u) {
                if(alphaCore[k] == 0) return 0;

                double d = diameter*Math.sqrt(1-Math.pow(u/nCore[k],2));

                double absExp = Math.exp(-alphaCore[k]*d);

                return (1-absExp)*alphaDopant[k]/alphaCore[k];
            }
        };

        for(k = 0 ; k < nCore.length ; k++) {
            result[k] = integrator.integrate();
        }

        return result;
    }

    public double[] reflections() {
        double[] result = new double[nCore.length];
        UnitIntegral integrator = new UnitIntegral() {
            @Override
            public double integrand(double u) {
                if(alphaCore[k] == 0) return 0;

                double d = diameter*Math.sqrt(1-Math.pow(u/nCore[k],2));

                double absExp = Math.exp(-alphaCore[k]*d);

                double cosAir = Math.sqrt(1-Math.pow(u,2));
                double cosCore = Math.sqrt(1-Math.pow(u/nCore[k],2));

                double fresnelR = Misc.fresnelR(cosAir, cosCore, 1, nCore[k]);

                return (1-fresnelR)*(1-absExp)/(1-fresnelR*absExp)*alphaDopant[k]/alphaCore[k];
            }
        };

        for(k = 0 ; k < nCore.length ; k++) {
            result[k] = integrator.integrate();
        }

        return result;
    }

    public double[] twoInterphases() {
        double[] result = new double[nCore.length];
        UnitIntegral integrator = new UnitIntegral() {
            @Override
            public double integrand(double u) {
                if(alphaCore[k] == 0) return 0;

                double dCore = diameter*Math.sqrt(q*q-Math.pow(u/nCore[k],2));
                double dClad = diameter/2*(Math.sqrt(1-Math.pow(u/nClad[k],2))-Math.sqrt(q*q-Math.pow(u/nClad[k],2)));

                double coreExp = Math.exp(-alphaCore[k]*dCore);
                double cladExp = Math.exp(-alphaClad[k]*dClad);

                double cosAir = Math.sqrt(1-Math.pow(u,2));
                double cosClad1 = Math.sqrt(1-Math.pow(u/nClad[k],2));
                double cosClad2 = Math.sqrt(1-Math.pow(u/q/nClad[k],2));
                double cosCore = Math.sqrt(1-Math.pow(u/q/nCore[k],2));

                double fresnelR1 = Misc.fresnelR(cosAir, cosClad1, 1, nClad[k]);
                double fresnelR2 = Misc.fresnelR(cosClad2, cosCore, nClad[k], nCore[k]);
                double fresnelT1 = 1-fresnelR1, fresnelT2 = 1-fresnelR2;

                double D1 = (1-fresnelR2*coreExp)*(1-fresnelR1*fresnelR2*cladExp*cladExp)-fresnelR1*fresnelT2*fresnelT2*cladExp*cladExp*coreExp;
                double D2 = fresnelT1*fresnelT2*cladExp*(1-coreExp);

                return D2/D1*alphaDopant[k]/alphaCore[k];
            }
        };

        for(k = 0 ; k < nCore.length ; k++) {
            double uCut = Math.min(1.0, q*nClad[k]);
            result[k] = integrator.integrate(uCut);
        }

        return result;
    }
}
