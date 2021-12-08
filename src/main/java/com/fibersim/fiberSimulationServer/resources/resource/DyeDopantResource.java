package com.fibersim.fiberSimulationServer.resources.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.DyeDopantResourceDeserializer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(using = DyeDopantResourceDeserializer.class)
public class DyeDopantResource {
    private final String dopant;
    private final double tauRad;
    private final double tauNR;
    private final double minLambda;
    private final double maxLambda;
    private final LambdaFunctionResource sigmaabs;
    private final LambdaFunctionResource sigmaemi;
}
