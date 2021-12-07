package com.fibersim.fiberSimulationServer.core.util;

public abstract class LambdaFunction {
    public abstract double eval(double lambda);

    public double[] getArray(double[] ll) {
        double[] result = new double[ll.length];

        for(int i = 0 ; i < ll.length ; i++) {
            result[i] = this.eval(ll[i]);
        }

        return result;
    }
}
