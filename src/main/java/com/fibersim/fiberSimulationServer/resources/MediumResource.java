package com.fibersim.fiberSimulationServer.resources;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.MediumResourceDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonDeserialize(using = MediumResourceDeserializer.class)
public class MediumResource {
    private String medium;
    private LambdaFunctionResource refractionIndex;
    private LambdaFunctionResource attenuation;
}
