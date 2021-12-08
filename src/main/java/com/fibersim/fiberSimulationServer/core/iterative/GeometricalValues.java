package com.fibersim.fiberSimulationServer.core.iterative;

public class GeometricalValues {
    public static double betaB(double refractionIndex) {
        return (refractionIndex-1)/(2*refractionIndex);
    }

    public static double KzB(double refractionIndex) {
        return 2*refractionIndex/(refractionIndex+1);
    }
}
