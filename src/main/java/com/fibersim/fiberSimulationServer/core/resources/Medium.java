package com.fibersim.fiberSimulationServer.core.resources;

import com.fibersim.fiberSimulationServer.core.utils.FunctionLL;
import com.fibersim.fiberSimulationServer.core.utils.SplineCSV;

public class Medium {
    private static final String MEDIUM_PREFIX = "";

    public FunctionLL refractionIndex;
    public FunctionLL attenuation;

    public Medium(String medium) {
        if(medium.equals("clad")) {
            refractionIndex = FunctionLL.constant(1.4);
            attenuation = new SplineCSV(MEDIUM_PREFIX+"/attenuationPMMA.csv", 500e-9, 0.01842*0.5);
        } else if(medium.equals("PMMA")) {
            refractionIndex = new FunctionLL() {
                @Override
                public double eval(double lambda) {
                    return Math.sqrt(1/(-8.226e-15/(lambda*lambda)+0.8393)+1);
                }
            };
            attenuation = new SplineCSV(MEDIUM_PREFIX+"/attenuationPMMA.csv", 500e-9, 0.01842);
        } else {
            refractionIndex = FunctionLL.constant(1);
            attenuation = FunctionLL.constant(0);
        }
    }
}
