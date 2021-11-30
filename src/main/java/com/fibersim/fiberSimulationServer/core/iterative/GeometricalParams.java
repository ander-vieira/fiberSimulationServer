package com.fibersim.fiberSimulationServer.core.iterative;

import org.springframework.stereotype.Component;

@Component
public class GeometricalParams {
    public double[] betaB(double[] n) {
        double[] result = new double[n.length];

        for(int k = 0 ; k < n.length ; k++) {
            result[k] = (n[k]-1)/(2*n[k]);
        }

        return result;
    }

    public double[] KzB(double[] n) {
        double[] result = new double[n.length];

        for(int k = 0 ; k < n.length ; k++) {
            result[k] = 2*n[k]/(n[k]+1);
        }

        return result;
    }
}
