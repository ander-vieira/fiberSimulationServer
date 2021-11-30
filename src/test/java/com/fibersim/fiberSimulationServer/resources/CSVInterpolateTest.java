package com.fibersim.fiberSimulationServer.resources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CSVInterpolateTest {
    @Test
    void csvInterpolateTest() {
        CSVInterpolator splineCSV = new CSVInterpolator("/sigmaabs_RhB.csv", 559.29e-9, 3.37e-20);

        double[] rawLambdas = splineCSV.getRawLambdas();
        double[] rawValues = splineCSV.getRawValues();

        for(int i = 0 ; i < splineCSV.getNumValues() ; i++) {
            Assertions.assertEquals(splineCSV.eval(rawLambdas[i]*1e-9), rawValues[i]*splineCSV.getScale(), 1e-12);
        }
    }
}
