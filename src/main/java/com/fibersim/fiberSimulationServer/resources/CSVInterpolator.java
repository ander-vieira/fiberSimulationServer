package com.fibersim.fiberSimulationServer.resources;

import com.fibersim.fiberSimulationServer.core.utils.FunctionLL;
import com.fibersim.fiberSimulationServer.resources.dto.CSVInterpolatorParamsDTO;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CSVInterpolator extends FunctionLL {
    private static final String CSV_PREFIX = "/csv";

    private static final boolean USE_FILTER = false;
    private static final double[] FILTER_LAMBDAS = {-3, -2, -1, 0, 1, 2, 3};
    private static final double[] FILTER_WEIGHTS = {0.076517, 0.133363, 0.186122, 0.207996, 0.186122, 0.133363, 0.076517};

    @Getter
    private final int numValues;
    @Getter
    private final double[] rawLambdas;
    @Getter
    private final double[] rawValues;
    @Getter
    private final double scale;
    @Getter
    private final boolean useFilter;

    private final double[] b;
    private final double[] c;
    private final double[] d;

    public CSVInterpolator(String filename, double peakLL, double peakValue) {
        this(filename, peakLL, peakValue, 0, 1);
    }

    public CSVInterpolator(String filename, double peakLL, double peakValue, int llCol, int valueCol) {
        this(CSVInterpolatorParamsDTO.builder()
                .filename(filename)
                .peakLL(peakLL)
                .peakValue(peakValue)
                .llColumn(llCol)
                .valueColumn(valueCol)
                .useFilter(USE_FILTER)
                .build());
    }

    public CSVInterpolator(CSVInterpolatorParamsDTO params) {
        int numValues = 0;

        Resource resource = new ClassPathResource(CSV_PREFIX+params.getFilename());

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

        this.useFilter = params.isUseFilter();

        try(BufferedReader csvReader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            for(int i = 0 ; i < numValues ; i++) {
                String line = csvReader.readLine();
                if(line != null) {
                    String[] elems = line.split(",");

                    rawLambdas[i] = Double.parseDouble(elems[params.getLlColumn()]);
                    rawValues[i] = Double.parseDouble(elems[params.getValueColumn()]);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        getSplineParams();

        this.scale = params.getPeakValue()/this.eval(params.getPeakLL(), 1, this.useFilter);
    }

    @Override
    public double eval(double lambda) {
        return this.eval(lambda, this.scale, this.useFilter);
    }

    private double eval(double lambda, double scale, boolean useFilter) {
        int i;

        lambda *= 1e9;

        if(useFilter) {
            double result = 0;

            for (int j = 0; j < FILTER_LAMBDAS.length; j++) {
                double lambdaNode = lambda + FILTER_LAMBDAS[j];
                result += eval(lambdaNode*1e-9, scale, false) * FILTER_WEIGHTS[j];
            }

            return result;
        } else {
            for(i = 0 ; i < numValues-1 ; i++) {
                if(lambda < rawLambdas[i]) break;
            }
            if(i == 0 || i == numValues) {
                return 0;
            } else {
                double result;

                result = (rawValues[i-1] + (rawValues[i]-rawValues[i-1])*(lambda-rawLambdas[i-1])/(rawLambdas[i]-rawLambdas[i-1]))*scale;
                //result = (rawValues[i - 1] + b[i - 1] * (lambda - rawLambdas[i - 1]) + c[i - 1] * Math.pow(lambda - rawLambdas[i - 1], 2) + d[i - 1] * Math.pow(lambda - rawLambdas[i - 1], 3)) * scale;

                if(result < 0) result = 0;

                return result;
            }
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
