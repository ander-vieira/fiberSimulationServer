package com.fibersim.fiberSimulationServer.resources.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.CSVInterpolatorParamsDTODeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonDeserialize(using = CSVInterpolatorParamsDTODeserializer.class)
public class CSVInterpolatorParamsDTO {
    private String filename;
    private double peakLL;
    private double peakValue;
    private int llColumn;
    private int valueColumn;
    private boolean useFilter;
}
