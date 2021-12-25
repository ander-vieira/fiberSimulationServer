package com.fibersim.fiberSimulationServer.dto.view;

import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;

@Getter
@Builder
public class PlotYDataDTO {
    private final String name;
    private final double[] data;

    public double getMaxValue() {
        return Arrays.stream(data).max().orElse(0);
    }
}
