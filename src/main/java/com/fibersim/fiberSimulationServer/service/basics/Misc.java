package com.fibersim.fiberSimulationServer.service.basics;

public class Misc {
    public static double fresnelR(double cos1, double cos2, double N1, double N2) {
        return (Math.pow((N1*cos1-N2*cos2)/(N1*cos1+N2*cos2), 2)+Math.pow((N2*cos1-N1*cos2)/(N2*cos1+N1*cos2), 2))/2;
    }

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
