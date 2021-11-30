package com.fibersim.fiberSimulationServer.core.resources;

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

    public void addDopant(DyeDopant dopant) {
        if(dopant.minLambda < minLambda) minLambda = dopant.minLambda;
        if(dopant.maxLambda > maxLambda) maxLambda = dopant.maxLambda;
    }

    public double[] getLL(int numLL) {
        if(numLL <= 1) {
            return null;
        } else {
            double[] result = new double[numLL];

            for(int i = 0 ; i < numLL ; i++) {
                result[i] = minLambda + (maxLambda-minLambda)*(i)/(numLL-1);
            }

            return result;
        }
    }

    public double getDlambda(int numLL) {
        if(numLL < 2) return 0;
        else return (maxLambda-minLambda)/(numLL-1);
    }
}