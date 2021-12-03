package com.fibersim.fiberSimulationServer.resources;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.LambdaCsvResourceDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonDeserialize(using = LambdaCsvResourceDeserializer.class)
public class LambdaCsvResource extends LambdaFunctionResource {
    private String filename;
    private double peakLL;
    private double peakValue;
    private int llColumn;
    private int valueColumn;
    private boolean useFilter;
    private CSVInterpolator csvInterpolator;

    @Override
    public double eval(double lambda) {
        if(csvInterpolator == null) {
            csvInterpolator = new CSVInterpolator(this);
        }

        return csvInterpolator.eval(lambda);
    }
}
