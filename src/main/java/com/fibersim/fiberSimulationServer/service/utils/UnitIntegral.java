package com.fibersim.fiberSimulationServer.service.utils;

import org.springframework.stereotype.Component;

@Component
public class UnitIntegral {
    private final int numPoints;

    public UnitIntegral() {
        this.numPoints = 10000;
    }

    public UnitIntegral(int numPoints) {
        this.numPoints = numPoints;
    }

    public double integrate(UnitIntegrand integrand, double uCut) {
        double result = 0;

        for(int i = 0 ; i < numPoints ; i++) {
            double uVal = uCut*(i+0.5)/numPoints;

            result += integrand.evaluate(uVal)/numPoints;
        }

        return result;
    }

    public double integrate(UnitIntegrand integrand) {
        return integrate(integrand,1);
    }
}
