package com.fibersim.fiberSimulationServer.service.iterative;

public abstract class UnitIntegral {
    final int numPoints = 10000;
    public abstract double integrand(double u);

    public double integrate(double uCut) {
        double result = 0;

        for(int i = 0 ; i < numPoints ; i++) {
            double uVal = uCut*(i+0.5)/numPoints;

            result += integrand(uVal)/numPoints;
        }

        return result;
    }

    public double integrate() {
        return integrate(1);
    }
}
