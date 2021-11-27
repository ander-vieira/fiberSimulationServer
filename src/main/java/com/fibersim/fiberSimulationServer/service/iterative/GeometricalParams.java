package com.fibersim.fiberSimulationServer.service.iterative;

import com.fibersim.fiberSimulationServer.service.resources.Medium;

public class GeometricalParams {
    private final double[] n;

    public GeometricalParams(Medium medium, double[] ll) {
        this(medium.refractionIndex.getArray(ll));
    }

    public GeometricalParams(double[] n) {
        this.n = n.clone();
    }

    public double[] betaB() {
        double[] result = new double[n.length];

        for(int k = 0 ; k < n.length ; k++) {
            result[k] = (n[k]-1)/(2*n[k]);
        }

        return result;
    }

    public double[] KzB() {
        double[] result = new double[n.length];

        for(int k = 0 ; k < n.length ; k++) {
            result[k] = 2*n[k]/(n[k]+1);
        }

        return result;
    }
}
