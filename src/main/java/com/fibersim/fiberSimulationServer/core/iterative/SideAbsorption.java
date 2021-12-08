package com.fibersim.fiberSimulationServer.core.iterative;

import com.fibersim.fiberSimulationServer.core.util.*;
import com.fibersim.fiberSimulationServer.dto.DyeDopantDTO;

public class SideAbsorption {
//    public LambdaFunction noReflections(double diameter, double q, double nCore, double alphaCore, double alphaDopant, double nClad, double alphaClad) {
//        return new LambdaFunction() {
//            @Override
//            public double eval(double lambda) {
//                UnitIntegrand integrand = u -> {
//                    if(alphaCore == 0) return 0;
//
//                    double d = diameter*Math.sqrt(1-Math.pow(u/nCore,2));
//
//                    double absExp = Math.exp(-alphaCore*d);
//
//                    return (1-absExp)*alphaDopant/alphaCore;
//                };
//
//                return unitIntegral.integrate(integrand);
//            }
//        };
//    }

//    public LambdaFunction reflections(double diameter, double q, double nCore, double alphaCore, double alphaDopant, double nClad, double alphaClad) {
//        return new LambdaFunction() {
//            @Override
//            public double eval(double lambda) {
//                UnitIntegrand integrand = u -> {
//                    if(alphaCore == 0) return 0;
//
//                    double d = diameter*Math.sqrt(1-Math.pow(u/nCore,2));
//
//                    double absExp = Math.exp(-alphaCore*d);
//
//                    double cosAir = Math.sqrt(1-Math.pow(u,2));
//                    double cosCore = Math.sqrt(1-Math.pow(u/nCore,2));
//
//                    double fresnelR = MathUtils.fresnelR(cosAir, cosCore, 1, nCore);
//
//                    return (1-fresnelR)*(1-absExp)/(1-fresnelR*absExp)*alphaDopant/alphaCore;
//                };
//
//                return unitIntegral.integrate(integrand);
//            }
//        };
//    }

//    public static LambdaFunction twoInterphases(double diameter, double q, DyeDopantDTO dyeDopantDTO, MediumResource mediumIn, MediumResource mediumOut) {
//        return new LambdaFunction() {
//            @Override
//            public double eval(double lambda) {
//                double nCore = mediumIn.getRefractionIndex().eval(lambda);
//                double alphaDopant = dyeDopantDTO.getDyeDopant().getSigmaabs().eval(lambda)*dyeDopantDTO.getConcentration();
//                double alphaCore = mediumIn.getAttenuation().eval(lambda)+alphaDopant;
//                double nClad = mediumOut.getRefractionIndex().eval(lambda);
//                double alphaClad = mediumOut.getAttenuation().eval(lambda);
//
//                UnitIntegrand integrand = u -> {
//                    if(alphaCore == 0) return 0;
//
//                    double dCore = diameter*Math.sqrt(q*q-Math.pow(u/nCore,2));
//                    double dClad = diameter/2*(Math.sqrt(1-Math.pow(u/nClad,2))-Math.sqrt(q*q-Math.pow(u/nClad,2)));
//
//                    double coreExp = Math.exp(-alphaCore*dCore);
//                    double cladExp = Math.exp(-alphaClad*dClad);
//
//                    double cosAir = Math.sqrt(1-Math.pow(u,2));
//                    double cosClad1 = Math.sqrt(1-Math.pow(u/nClad,2));
//                    double cosClad2 = Math.sqrt(1-Math.pow(u/q/nClad,2));
//                    double cosCore = Math.sqrt(1-Math.pow(u/q/nCore,2));
//
//                    double fresnelR1 = MathUtils.fresnelR(cosAir, cosClad1, 1, nClad);
//                    double fresnelR2 = MathUtils.fresnelR(cosClad2, cosCore, nClad, nCore);
//                    double fresnelT1 = 1-fresnelR1, fresnelT2 = 1-fresnelR2;
//
//                    double D1 = (1-fresnelR2*coreExp)*(1-fresnelR1*fresnelR2*cladExp*cladExp)-fresnelR1*fresnelT2*fresnelT2*cladExp*cladExp*coreExp;
//                    double D2 = fresnelT1*fresnelT2*cladExp*(1-coreExp);
//
//                    return D2/D1*alphaDopant/alphaCore;
//                };
//
//                return UnitIntegral.integrate(integrand);
//            }
//        };
//    }

    public static double twoInterphases(SideAbsorptionParams params) {
        double result = 0, concentrationToPower, irradiance, sideEfficiency;
        DyeDopantDTO dyeDopantDTO = params.getDyeDopants().get(0);

        for(double lambda: params.getLl()) {
            double nCore = params.getMediumIn().getRefractionIndex().eval(lambda);
            double alphaDopant = dyeDopantDTO.getDyeDopant().getSigmaabs().eval(lambda)*dyeDopantDTO.getConcentration();
            double alphaCore = params.getMediumIn().getAttenuation().eval(lambda)+alphaDopant;
            double nClad = params.getMediumOut().getRefractionIndex().eval(lambda);
            double alphaClad = params.getMediumOut().getAttenuation().eval(lambda);

            UnitIntegrand integrand = u -> {
                if(alphaCore == 0) return 0;

                double dCore = params.getDiameter()*Math.sqrt(Math.pow(params.getCladRatio(),2)-Math.pow(u/nCore,2));
                double dClad = params.getDiameter()/2*(Math.sqrt(1-Math.pow(u/nClad,2))-Math.sqrt(Math.pow(params.getCladRatio(),2)-Math.pow(u/nClad,2)));

                double coreExp = Math.exp(-alphaCore*dCore);
                double cladExp = Math.exp(-alphaClad*dClad);

                double cosAir = Math.sqrt(1-Math.pow(u,2));
                double cosClad1 = Math.sqrt(1-Math.pow(u/nClad,2));
                double cosClad2 = Math.sqrt(1-Math.pow(u/params.getCladRatio()/nClad,2));
                double cosCore = Math.sqrt(1-Math.pow(u/params.getCladRatio()/nCore,2));

                double fresnelR1 = MathUtils.fresnelR(cosAir, cosClad1, 1, nClad);
                double fresnelR2 = MathUtils.fresnelR(cosClad2, cosCore, nClad, nCore);
                double fresnelT1 = 1-fresnelR1, fresnelT2 = 1-fresnelR2;

                double D1 = (1-fresnelR2*coreExp)*(1-fresnelR1*fresnelR2*cladExp*cladExp)-fresnelR1*fresnelT2*fresnelT2*cladExp*cladExp*coreExp;
                double D2 = fresnelT1*fresnelT2*cladExp*(1-coreExp);

                return D2/D1*alphaDopant/alphaCore;
            };

            concentrationToPower = Math.PI*Constants.h*Constants.c*Math.pow(params.getDiameter(),2)/(4*lambda);
            irradiance = params.getPowerSource().getIrradiance().eval(lambda);
            sideEfficiency = UnitIntegral.integrate(integrand);

            result += params.getDiameter()*irradiance*sideEfficiency*params.getDLambda()/concentrationToPower;
        }

        return result;
    }
}
