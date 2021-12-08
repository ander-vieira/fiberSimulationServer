package com.fibersim.fiberSimulationServer.core.iterative;

import com.fibersim.fiberSimulationServer.dto.DyeDopantDTO;
import com.fibersim.fiberSimulationServer.resources.resource.MediumResource;
import com.fibersim.fiberSimulationServer.resources.resource.PowerSourceResource;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SideAbsorptionParams {
    private PowerSourceResource powerSource;
    private List<DyeDopantDTO> dyeDopants;
    private MediumResource mediumIn;
    private MediumResource mediumOut;
    private double diameter;
    private double cladRatio;
    private double dLambda;
    private double[] ll;
}
