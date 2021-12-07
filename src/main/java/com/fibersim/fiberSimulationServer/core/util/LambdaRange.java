package com.fibersim.fiberSimulationServer.core.util;

import com.fibersim.fiberSimulationServer.resources.resource.DyeDopantResource;

public class LambdaRange {
    public double minLambda;
    public double maxLambda;

    public LambdaRange() {
        this(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    }

    public LambdaRange(double minLambda, double maxLambda) {
        this.minLambda = minLambda;
        this.maxLambda = maxLambda;
    }

    public void add(double minLambda, double maxLambda) {
        if(minLambda < this.minLambda) this.minLambda = minLambda;
        if(maxLambda > this.maxLambda) this.maxLambda = maxLambda;
    }

    public void addDopant(DyeDopantResource dopant) {
        add(dopant.getMinLambda(), dopant.getMaxLambda());
    }

    public double[] getLL(int numLambda) {
        if(numLambda <= 1 || maxLambda < minLambda) {
            return null;
        } else {
            double[] result = new double[numLambda];

            for(int i = 0 ; i < numLambda ; i++) {
                result[i] = minLambda + (maxLambda-minLambda)*(i)/(numLambda-1);
            }

            return result;
        }
    }

    public double getDLambda(int numLambda) {
        if(numLambda <= 1 || maxLambda < minLambda) return 0;
        else return (maxLambda-minLambda)/(numLambda-1);
    }
}
