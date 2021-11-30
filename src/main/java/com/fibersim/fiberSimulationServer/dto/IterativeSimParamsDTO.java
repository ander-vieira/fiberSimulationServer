package com.fibersim.fiberSimulationServer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IterativeSimParamsDTO {
    private String dopant;
    private double concentration;
    private double diameter;
    private double cladRatio;
    private double length;
    private double darkLength;
}
