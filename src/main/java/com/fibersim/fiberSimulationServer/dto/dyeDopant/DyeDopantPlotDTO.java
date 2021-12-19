package com.fibersim.fiberSimulationServer.dto.dyeDopant;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DyeDopantPlotDTO {
    private final double[] lambda;
    private final double[] sigmaAbs;
    private final double[] sigmaEmi;
}
