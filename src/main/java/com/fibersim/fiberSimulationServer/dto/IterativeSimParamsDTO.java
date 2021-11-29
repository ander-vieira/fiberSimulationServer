package com.fibersim.fiberSimulationServer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IterativeSimParamsDTO {
    private String dopant;
    private double concentration;
    private double diameter;
    private double cladRatio;
    private double length;
    private double darkLength;
}
