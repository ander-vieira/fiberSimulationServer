package com.fibersim.fiberSimulationServer.core.util;

public class MathUtils {
    public static double fresnelR(double cos1, double cos2, double N1, double N2) {
        return (Math.pow((N1*cos1-N2*cos2)/(N1*cos1+N2*cos2), 2)+Math.pow((N2*cos1-N1*cos2)/(N2*cos1+N1*cos2), 2))/2;
    }
}
