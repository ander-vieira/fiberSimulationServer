package com.fibersim.fiberSimulationServer.resources.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.PowerSourceResourceDeserializer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(using = PowerSourceResourceDeserializer.class)
public class PowerSourceResource {
    private final String source;
    private final LambdaFunctionResource irradiance;
}
