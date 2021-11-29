package com.fibersim.fiberSimulationServer.service.iterative;

import com.fibersim.fiberSimulationServer.service.utils.MathUtils;
import com.fibersim.fiberSimulationServer.service.utils.UnitIntegral;
import com.fibersim.fiberSimulationServer.service.utils.UnitIntegrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SideAbsorption {
    private int k;

    @Autowired
    private UnitIntegral unitIntegral;

    public double[] noReflections(double diameter, double q, double[] nCore, double[] alphaCore, double[] alphaDopant, double[] nClad, double[] alphaClad) {
        double[] result = new double[nCore.length];

        UnitIntegrand integrand = u -> {
            if(alphaCore[k] == 0) return 0;

            double d = diameter*Math.sqrt(1-Math.pow(u/nCore[k],2));

            double absExp = Math.exp(-alphaCore[k]*d);

            return (1-absExp)*alphaDopant[k]/alphaCore[k];
        };

        for(k = 0 ; k < nCore.length ; k++) {
            result[k] = unitIntegral.integrate(integrand);
        }

        return result;
    }

    public double[] reflections(double diameter, double q, double[] nCore, double[] alphaCore, double[] alphaDopant, double[] nClad, double[] alphaClad) {
        double[] result = new double[nCore.length];

        UnitIntegrand integrand = u -> {
            if(alphaCore[k] == 0) return 0;

            double d = diameter*Math.sqrt(1-Math.pow(u/nCore[k],2));

            double absExp = Math.exp(-alphaCore[k]*d);

            double cosAir = Math.sqrt(1-Math.pow(u,2));
            double cosCore = Math.sqrt(1-Math.pow(u/nCore[k],2));

            double fresnelR = MathUtils.fresnelR(cosAir, cosCore, 1, nCore[k]);

            return (1-fresnelR)*(1-absExp)/(1-fresnelR*absExp)*alphaDopant[k]/alphaCore[k];
        };

        for(k = 0 ; k < nCore.length ; k++) {
            result[k] = unitIntegral.integrate(integrand);
        }

        return result;
    }

    public double[] twoInterphases(double diameter, double q, double[] nCore, double[] alphaCore, double[] alphaDopant, double[] nClad, double[] alphaClad) {
        double[] result = new double[nCore.length];

        UnitIntegrand integrand = u -> {
            if(alphaCore[k] == 0) return 0;

            double dCore = diameter*Math.sqrt(q*q-Math.pow(u/nCore[k],2));
            double dClad = diameter/2*(Math.sqrt(1-Math.pow(u/nClad[k],2))-Math.sqrt(q*q-Math.pow(u/nClad[k],2)));

            double coreExp = Math.exp(-alphaCore[k]*dCore);
            double cladExp = Math.exp(-alphaClad[k]*dClad);

            double cosAir = Math.sqrt(1-Math.pow(u,2));
            double cosClad1 = Math.sqrt(1-Math.pow(u/nClad[k],2));
            double cosClad2 = Math.sqrt(1-Math.pow(u/q/nClad[k],2));
            double cosCore = Math.sqrt(1-Math.pow(u/q/nCore[k],2));

            double fresnelR1 = MathUtils.fresnelR(cosAir, cosClad1, 1, nClad[k]);
            double fresnelR2 = MathUtils.fresnelR(cosClad2, cosCore, nClad[k], nCore[k]);
            double fresnelT1 = 1-fresnelR1, fresnelT2 = 1-fresnelR2;

            double D1 = (1-fresnelR2*coreExp)*(1-fresnelR1*fresnelR2*cladExp*cladExp)-fresnelR1*fresnelT2*fresnelT2*cladExp*cladExp*coreExp;
            double D2 = fresnelT1*fresnelT2*cladExp*(1-coreExp);

            return D2/D1*alphaDopant[k]/alphaCore[k];
        };

        for(k = 0 ; k < nCore.length ; k++) {
            double uCut = Math.min(1.0, q*nClad[k]);
            result[k] = unitIntegral.integrate(integrand, uCut);
        }

        return result;
    }
}
