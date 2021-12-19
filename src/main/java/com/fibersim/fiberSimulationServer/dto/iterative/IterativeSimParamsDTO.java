package com.fibersim.fiberSimulationServer.dto.iterative;

import com.fibersim.fiberSimulationServer.dto.DyeDopantParamsDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class IterativeSimParamsDTO {
    private List<DyeDopantParamsDTO> dyeDopants;
    private double diameter;
    private double cladRatio;
    private double length;
    private double darkLength;
}
