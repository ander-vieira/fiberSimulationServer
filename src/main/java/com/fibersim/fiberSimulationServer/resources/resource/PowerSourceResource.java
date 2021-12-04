package com.fibersim.fiberSimulationServer.resources.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.PowerSourceResourceDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonDeserialize(using = PowerSourceResourceDeserializer.class)
public class PowerSourceResource {
    private String source;
    private LambdaFunctionResource irradiance;
}
