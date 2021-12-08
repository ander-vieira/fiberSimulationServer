package com.fibersim.fiberSimulationServer.resources.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.MediumResourceDeserializer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(using = MediumResourceDeserializer.class)
public class MediumResource {
    private final String medium;
    private final LambdaFunctionResource refractionIndex;
    private final LambdaFunctionResource attenuation;
}
