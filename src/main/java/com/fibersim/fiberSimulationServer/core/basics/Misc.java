package com.fibersim.fiberSimulationServer.core.basics;

public class Misc {
    public static double[] getLambdaRange(double minLambda, double maxLambda, int numLL) {
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
}
