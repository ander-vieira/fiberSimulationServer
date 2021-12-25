package com.fibersim.fiberSimulationServer.dto.dyeDopant;

import com.fibersim.fiberSimulationServer.resources.resource.DyeDopantResource;
import lombok.Getter;

@Getter
public class DyeDopantDataDTO {
    private final String name;
    private final double tauRad;
    private final double tauNR;
    private final double minLambda;
    private final double maxLambda;

    public DyeDopantDataDTO(DyeDopantResource dyeDopantResource) {
        this.name = dyeDopantResource.getDopant();
        this.tauRad = dyeDopantResource.getTauRad();
        this.tauNR = dyeDopantResource.getTauNR();
        this.minLambda = dyeDopantResource.getMinLambda();
        this.maxLambda = dyeDopantResource.getMaxLambda();
    }

    public double getQuantumEfficiency() {
        return tauNR/(tauNR+tauRad);
    }
}
