package com.fibersim.fiberSimulationServer.dto;

import com.fibersim.fiberSimulationServer.resources.resource.DyeDopantResource;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DyeDopantDTO {
    private DyeDopantResource dyeDopant;
    private double concentration;
}
