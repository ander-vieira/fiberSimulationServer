package com.fibersim.fiberSimulationServer.resources;

import com.fibersim.fiberSimulationServer.resources.interpolate.CSVInterpolator;
import com.fibersim.fiberSimulationServer.resources.resource.LambdaCsvResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CSVInterpolatorTest {
    @Test
    void csvInterpolatorTest() {
        CSVInterpolator csvInterpolator = new CSVInterpolator(LambdaCsvResource.builder()
                .filename("sigmaabs_Rh6G")
                .peakLL(530.58e-9)
                .peakValue(4.3298e-20)
                .llColumn(0)
                .valueColumn(1)
                .build());

        double[] rawLambdas = csvInterpolator.getRawLambdas();
        double[] rawValues = csvInterpolator.getRawValues();

        for(int i = 0 ; i < csvInterpolator.getNumValues() ; i++) {
            Assertions.assertEquals(csvInterpolator.eval(rawLambdas[i]*1e-9), rawValues[i]*csvInterpolator.getScale(), 1e-12);
        }
    }
}
