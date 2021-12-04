package com.fibersim.fiberSimulationServer.resources.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.LambdaConstantResourceDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonDeserialize(using = LambdaConstantResourceDeserializer.class)
public class LambdaConstantResource extends LambdaFunctionResource {
    private double value;

    @Override
    public double eval(double lambda) {
        return value;
    }
}
