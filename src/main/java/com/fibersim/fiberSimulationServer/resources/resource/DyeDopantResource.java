package com.fibersim.fiberSimulationServer.resources.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.DyeDopantResourceDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonDeserialize(using = DyeDopantResourceDeserializer.class)
public class DyeDopantResource {
    private String dopant;
    private double tauRad;
    private double tauNR;
    private double minLambda;
    private double maxLambda;
    private LambdaFunctionResource sigmaabs;
    private LambdaFunctionResource sigmaemi;
}
