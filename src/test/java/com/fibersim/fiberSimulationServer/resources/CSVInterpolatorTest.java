package com.fibersim.fiberSimulationServer.resources;

import com.fibersim.fiberSimulationServer.resources.interpolate.CSVInterpolator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CSVInterpolatorTest {
    @Test
    void csvInterpolatorTest() {
        CSVInterpolator csvInterpolator = new CSVInterpolator("sigmaabs_Rh6G", 530.58e-9, 4.3298e-20);

        double[] rawLambdas = csvInterpolator.getRawLambdas();
        double[] rawValues = csvInterpolator.getRawValues();

        for(int i = 0 ; i < csvInterpolator.getNumValues() ; i++) {
            Assertions.assertEquals(csvInterpolator.eval(rawLambdas[i]*1e-9), rawValues[i]*csvInterpolator.getScale(), 1e-12);
        }
    }
}
