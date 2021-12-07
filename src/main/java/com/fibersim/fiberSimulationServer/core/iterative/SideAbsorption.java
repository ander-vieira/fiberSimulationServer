package com.fibersim.fiberSimulationServer.core.iterative;

import com.fibersim.fiberSimulationServer.core.util.LambdaFunction;
import com.fibersim.fiberSimulationServer.core.util.MathUtils;
import com.fibersim.fiberSimulationServer.core.util.UnitIntegral;
import com.fibersim.fiberSimulationServer.core.util.UnitIntegrand;
import com.fibersim.fiberSimulationServer.dto.DyeDopantDTO;
import com.fibersim.fiberSimulationServer.resources.resource.MediumResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SideAbsorption {
    @Autowired
    private UnitIntegral unitIntegral;

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

    public LambdaFunction twoInterphases(double diameter, double q, DyeDopantDTO dyeDopantDTO, MediumResource mediumIn, MediumResource mediumOut) {
        return new LambdaFunction() {
            @Override
            public double eval(double lambda) {
                double nCore = mediumIn.getRefractionIndex().eval(lambda);
                double alphaDopant = dyeDopantDTO.getDyeDopant().getSigmaabs().eval(lambda)*dyeDopantDTO.getConcentration();
                double alphaCore = mediumIn.getAttenuation().eval(lambda)+alphaDopant;
                double nClad = mediumOut.getRefractionIndex().eval(lambda);
                double alphaClad = mediumOut.getAttenuation().eval(lambda);

                UnitIntegrand integrand = u -> {
                    if(alphaCore == 0) return 0;

                    double dCore = diameter*Math.sqrt(q*q-Math.pow(u/nCore,2));
                    double dClad = diameter/2*(Math.sqrt(1-Math.pow(u/nClad,2))-Math.sqrt(q*q-Math.pow(u/nClad,2)));

                    double coreExp = Math.exp(-alphaCore*dCore);
                    double cladExp = Math.exp(-alphaClad*dClad);

                    double cosAir = Math.sqrt(1-Math.pow(u,2));
                    double cosClad1 = Math.sqrt(1-Math.pow(u/nClad,2));
                    double cosClad2 = Math.sqrt(1-Math.pow(u/q/nClad,2));
                    double cosCore = Math.sqrt(1-Math.pow(u/q/nCore,2));

                    double fresnelR1 = MathUtils.fresnelR(cosAir, cosClad1, 1, nClad);
                    double fresnelR2 = MathUtils.fresnelR(cosClad2, cosCore, nClad, nCore);
                    double fresnelT1 = 1-fresnelR1, fresnelT2 = 1-fresnelR2;

                    double D1 = (1-fresnelR2*coreExp)*(1-fresnelR1*fresnelR2*cladExp*cladExp)-fresnelR1*fresnelT2*fresnelT2*cladExp*cladExp*coreExp;
                    double D2 = fresnelT1*fresnelT2*cladExp*(1-coreExp);

                    return D2/D1*alphaDopant/alphaCore;
                };

                return unitIntegral.integrate(integrand);
            }
        };
    }
}
