package com.fibersim.fiberSimulationServer.core.iterative;

import com.fibersim.fiberSimulationServer.resources.resource.MediumResource;

import java.util.Arrays;

public class IterativeAttenuator {
    private final double[] Pattconst;

    public IterativeAttenuator(MediumResource medium, double[] ll, double dz) {
        Pattconst = Arrays.stream(ll)
                .map(lambda -> -GeometricalParams.KzB(medium.getRefractionIndex().eval(lambda))*medium.getAttenuation().eval(lambda)*dz)
                .toArray();
    }

    public double updateP(double oldP, int k) {
        return oldP*Pattconst[k];
    }
}
