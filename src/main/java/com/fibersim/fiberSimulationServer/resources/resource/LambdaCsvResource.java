package com.fibersim.fiberSimulationServer.resources.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.interpolate.CSVInterpolator;
import com.fibersim.fiberSimulationServer.resources.jackson.LambdaCsvResourceDeserializer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(using = LambdaCsvResourceDeserializer.class)
public class LambdaCsvResource extends LambdaFunctionResource {
    private final String filename;
    private final double peakLL;
    private final double peakValue;
    private final int llColumn;
    private final int valueColumn;
    private CSVInterpolator csvInterpolator;

    @Override
    public double eval(double lambda) {
        if(csvInterpolator == null) {
            csvInterpolator = new CSVInterpolator(this);
        }

        return csvInterpolator.eval(lambda);
    }
}
