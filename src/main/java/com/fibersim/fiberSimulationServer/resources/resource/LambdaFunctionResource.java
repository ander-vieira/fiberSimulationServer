package com.fibersim.fiberSimulationServer.resources.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.LambdaFunctionResourceDeserializer;

@JsonDeserialize(using = LambdaFunctionResourceDeserializer.class)
public abstract class LambdaFunctionResource {
    public abstract double eval(double lambda);

    public double[] getArray(double[] ll) {
        double[] result = new double[ll.length];

        for(int i = 0 ; i < ll.length ; i++) {
            result[i] = this.eval(ll[i]);
        }

        return result;
    }
}
