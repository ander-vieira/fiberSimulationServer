package com.fibersim.fiberSimulationServer.dto.view;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlotDTO {
    private final double[] XData;
    private final List<PlotYDataDTO> YDataList;

    public double getMaxYValue() {
        return YDataList.stream().map(PlotYDataDTO::getMaxValue).max(Double::compare).orElse((double)0);
    }
}
