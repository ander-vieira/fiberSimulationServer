package com.fibersim.fiberSimulationServer.dto.iterative;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IterativeSimResponseDTO {
    private double elapsedTime;
    private double lightP;
}
