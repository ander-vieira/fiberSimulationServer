package com.fibersim.fiberSimulationServer.core.resources;

import com.fibersim.fiberSimulationServer.resources.resource.LambdaCsvResource;
import com.fibersim.fiberSimulationServer.resources.resource.LambdaFunctionResource;

public class PowerSource {
    private static final String SOURCE_PREFIX = "";

    public LambdaFunctionResource I;

    public PowerSource(String source) {
        switch(source) {
            default:
                I = LambdaCsvResource.builder()
                        .filename(SOURCE_PREFIX+"solarIrradiance")
                        .peakLL(500e-9)
                        .peakValue(1.5297e9)
                        .llColumn(0)
                        .valueColumn(2)
                        .build();
                break;
        }
    }
}
