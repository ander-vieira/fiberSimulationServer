package com.fibersim.fiberSimulationServer.core.resources;

import com.fibersim.fiberSimulationServer.resources.LambdaCsvResource;
import com.fibersim.fiberSimulationServer.resources.LambdaConstantResource;
import com.fibersim.fiberSimulationServer.resources.LambdaFunctionResource;

public class Medium {
    private static final String MEDIUM_PREFIX = "";

    public LambdaFunctionResource refractionIndex;
    public LambdaFunctionResource attenuation;

    public Medium(String medium) {
        if(medium.equals("clad")) {
            refractionIndex = LambdaConstantResource.builder()
                    .value(1.4)
                    .build();
            attenuation = LambdaCsvResource.builder()
                    .filename(MEDIUM_PREFIX+"attenuationPMMA")
                    .peakLL(500e-9)
                    .peakValue(0.01842*0.5)
                    .llColumn(0)
                    .valueColumn(1)
                    .build();
        } else if(medium.equals("PMMA")) {
            refractionIndex = new LambdaFunctionResource() {
                @Override
                public double eval(double lambda) {
                    return Math.sqrt(1/(-8.226e-15/(lambda*lambda)+0.8393)+1);
                }
            };
            attenuation = LambdaCsvResource.builder()
                    .filename(MEDIUM_PREFIX+"attenuationPMMA")
                    .peakLL(500e-9)
                    .peakValue(0.01842)
                    .llColumn(0)
                    .valueColumn(1)
                    .build();
        } else {
            refractionIndex = LambdaConstantResource.builder()
                    .value(1)
                    .build();
            attenuation = LambdaConstantResource.builder()
                    .value(0)
                    .build();
        }
    }
}
