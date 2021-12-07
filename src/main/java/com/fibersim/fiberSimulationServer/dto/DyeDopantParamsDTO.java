package com.fibersim.fiberSimulationServer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DyeDopantParamsDTO {
    private String dopant;
    private double concentration;
}
