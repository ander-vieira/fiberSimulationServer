package com.fibersim.fiberSimulationServer.core.iterative;

import com.fibersim.fiberSimulationServer.dto.DyeDopantDTO;
import com.fibersim.fiberSimulationServer.resources.resource.MediumResource;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IterativeDyeDopantParams {
    private DyeDopantDTO dyeDopantDTO;
    private MediumResource medium;
    private double diameter;
    private double dz;
    private double Nsolconst;
    private int numZZ;
    private double[] ll;
}
