package com.fibersim.fiberSimulationServer.resources.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.DyeDopantParamsDTODeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonDeserialize(using = DyeDopantParamsDTODeserializer.class)
public class DyeDopantParamsDTO {
    private String dopant;
    private double tauRad;
    private double tauNR;
    private double minLambda;
    private double maxLambda;
    private CSVInterpolatorParamsDTO sigmaabs;
    private CSVInterpolatorParamsDTO sigmaemi;
}
