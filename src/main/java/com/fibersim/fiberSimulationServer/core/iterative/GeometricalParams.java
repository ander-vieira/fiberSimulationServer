package com.fibersim.fiberSimulationServer.core.iterative;

import org.springframework.stereotype.Component;

@Component
public class GeometricalParams {
    public double betaB(double refractionIndex) {
        return (refractionIndex-1)/(2*refractionIndex);
    }

    public double KzB(double refractionIndex) {
        return 2*refractionIndex/(refractionIndex+1);
    }
}
