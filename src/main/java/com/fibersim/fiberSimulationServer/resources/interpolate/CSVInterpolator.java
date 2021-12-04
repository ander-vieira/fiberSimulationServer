package com.fibersim.fiberSimulationServer.resources.interpolate;

import com.fibersim.fiberSimulationServer.resources.resource.LambdaCsvResource;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CSVInterpolator {
    private static final String CSV_PREFIX = "/csv/";
    private static final String CSV_SUFFIX = ".csv";

    //private static final double[] FILTER_LAMBDAS = {-3, -2, -1, 0, 1, 2, 3};
    //private static final double[] FILTER_WEIGHTS = {0.076517, 0.133363, 0.186122, 0.207996, 0.186122, 0.133363, 0.076517};

    @Getter
    private final int numValues;
    @Getter
    private final double[] rawLambdas;
    @Getter
    private final double[] rawValues;
    @Getter
    private final double scale;

    public CSVInterpolator(LambdaCsvResource params) {
        int numValues = 0;

        Resource resource = new ClassPathResource(CSV_PREFIX+params.getFilename()+CSV_SUFFIX);

        try(BufferedReader csvReader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            while(csvReader.readLine() != null) numValues++;
        } catch(IOException e) {
            e.printStackTrace();
        }

        this.numValues = numValues;
        this.rawLambdas = new double[numValues];
        this.rawValues = new double[numValues];

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

        this.scale = params.getPeakValue()/this.eval(params.getPeakLL(), 1);
    }

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
            double result;

            result = (rawValues[i-1] + (rawValues[i]-rawValues[i-1])*(lambda-rawLambdas[i-1])/(rawLambdas[i]-rawLambdas[i-1]))*scale;

            if(result < 0) result = 0;

            return result;
        }
    }
}
