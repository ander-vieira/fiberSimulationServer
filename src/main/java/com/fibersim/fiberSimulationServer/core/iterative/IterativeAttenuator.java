package com.fibersim.fiberSimulationServer.core.iterative;

import com.fibersim.fiberSimulationServer.resources.resource.MediumResource;

public class IterativeAttenuator {
    private final double[] Pattconst;

    public IterativeAttenuator(MediumResource medium, double[] ll, double dz) {
        Pattconst = new double[ll.length];
        for(int k = 0 ; k < ll.length ; k++) {
            Pattconst[k] = -GeometricalValues.KzB(medium.getRefractionIndex().eval(ll[k]))*medium.getAttenuation().eval(ll[k])*dz;
        }
    }

    public double updateP(double oldP, int k) {
        return oldP*Pattconst[k];
    }
}
