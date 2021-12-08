package com.fibersim.fiberSimulationServer.core.util;

public class UnitIntegral {
    private static final int NUM_POINTS = 10000;

    public static double integrate(UnitIntegrand integrand, double uCut) {
        double result = 0;

        for(int i = 0; i < NUM_POINTS; i++) {
            double uVal = uCut*(i+0.5)/ NUM_POINTS;

            result += integrand.evaluate(uVal)/ NUM_POINTS;
        }

        return result;
    }

    public static double integrate(UnitIntegrand integrand) {
        return integrate(integrand,1);
    }
}
