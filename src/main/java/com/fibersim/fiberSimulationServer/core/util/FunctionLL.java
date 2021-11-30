package com.fibersim.fiberSimulationServer.core.util;

public abstract class FunctionLL {
    public abstract double eval(double lambda);

    public double[] getArray(double[] ll) {
        double[] result = new double[ll.length];

        for(int i = 0 ; i < ll.length ; i++) {
            result[i] = this.eval(ll[i]);
        }

        return result;
    }

    public static FunctionLL constant(double value) {
        return new FunctionLL() {
            @Override
            public double eval(double lambda) {
                return value;
            }
        };
    }
}
