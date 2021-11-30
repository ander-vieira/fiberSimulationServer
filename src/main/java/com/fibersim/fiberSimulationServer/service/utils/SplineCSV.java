package com.fibersim.fiberSimulationServer.service.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SplineCSV extends FunctionLL {
    private static final String CSV_PREFIX = "/csv";

    private static final boolean USE_FILTER = true;
    private static final double[] FILTER_LAMBDAS = {-3, -2, -1, 0, 1, 2, 3};
    private static final double[] FILTER_WEIGHTS = {0.076517, 0.133363, 0.186122, 0.207996, 0.186122, 0.133363, 0.076517};

    private final int numValues;
    private final double[] rawLambdas;
    private final double[] rawValues;
    private final double[] b;
    private final double[] c;
    private final double[] d;
    private final double scale;

    public SplineCSV(String filename, double peakLL, double peakValue) {
        this(filename, peakLL, peakValue, 0, 1);
    }

    public SplineCSV(String filename, double peakLL, double peakValue, int llCol, int valueCol) {
        int numValues = 0;

        Resource resource = new ClassPathResource(CSV_PREFIX+filename);

        try(BufferedReader csvReader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            while(csvReader.readLine() != null) numValues++;
        } catch(IOException e) {
            e.printStackTrace();
        }

        this.numValues = numValues;
        this.rawLambdas = new double[numValues];
        this.rawValues = new double[numValues];
        b = new double[numValues-1];
        c = new double[numValues];
        d = new double[numValues-1];

        try(BufferedReader csvReader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            for(int i = 0 ; i < numValues ; i++) {
                String line = csvReader.readLine();
                if(line != null) {
                    String[] elems = line.split(",");

                    rawLambdas[i] = Double.parseDouble(elems[llCol]);
                    rawValues[i] = Double.parseDouble(elems[valueCol]);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        getSplineParams();

        this.scale = peakValue/this.eval(peakLL, 1);
    }

    @Override
    public double eval(double lambda) {
        return this.eval(lambda, this.scale);
    }

    private double eval(double lambda, double scale) {
        int i;

        lambda *= 1e9;

        for(i = 0 ; i < numValues-1 ; i++) {
            if(lambda < rawLambdas[i]) break;
        }

        if(i == 0 || i == numValues) {
            return 0;
        } else {
            double result = 0;
            if (USE_FILTER) {
                for (int j = 0; j < FILTER_LAMBDAS.length; j++) {
                    double lambdaNode = lambda + FILTER_LAMBDAS[j];
                    result += (rawValues[i - 1] + b[i - 1] * (lambdaNode - rawLambdas[i - 1]) + c[i - 1] * Math.pow(lambdaNode - rawLambdas[i - 1], 2) + d[i - 1] * Math.pow(lambdaNode - rawLambdas[i - 1], 3)) * scale * FILTER_WEIGHTS[j];
                }
            } else {
                result = (rawValues[i - 1] + b[i - 1] * (lambda - rawLambdas[i - 1]) + c[i - 1] * Math.pow(lambda - rawLambdas[i - 1], 2) + d[i - 1] * Math.pow(lambda - rawLambdas[i - 1], 3)) * scale;
            }

            if(result < 0) result = 0;

            return result;
        }
    }

    private void getSplineParams() {
        double[] v1 = new double[numValues];
        double[] A1 = new double[numValues];
        double[] A2 = new double[numValues];
        double[] A3 = new double[numValues];

        for(int i = 0 ; i < numValues ; i++) {
            if(i == 0 || i == numValues-1) {
                v1[i] = 0;
            } else {
                double deltaX1 = rawLambdas[i]-rawLambdas[i-1];
                double deltaX2 = rawLambdas[i+1]-rawLambdas[i];
                double deltaY1 = rawValues[i]-rawValues[i-1];
                double deltaY2 = rawValues[i+1]-rawValues[i];

                A1[i] = deltaX1;
                A2[i] = 2*(deltaX1+deltaX2);
                A3[i] = deltaX2;
                v1[i] = 3*(deltaY2/deltaX2-deltaY1/deltaX1);
            }
        }

        double[] oldC = new double[numValues];

        for(int k = 0 ; k < 20 ; k++) {
            for(int i = 0 ; i < numValues ; i++) {
                oldC[i] = c[i];

                if(i == 0 || i == numValues-1) {
                    c[i] = 0;
                } else {
                    c[i] = (v1[i]-A1[i]*oldC[i-1]-A3[i]*oldC[i+1])/A2[i];
                }
            }
        }

        for(int i = 0 ; i < numValues-1 ; i++) {
            d[i] = (c[i+1]-c[i])/(3*(rawLambdas[i+1]-rawLambdas[i]));
            b[i] = (rawValues[i+1]-rawValues[i])/(rawLambdas[i+1]-rawLambdas[i])-(rawLambdas[i+1]-rawLambdas[i])*(2*c[i]+c[i+1])/3;
        }
    }
}
