package com.fibersim.fiberSimulationServer.service.resources;

import com.fibersim.fiberSimulationServer.service.utils.FunctionLL;
import com.fibersim.fiberSimulationServer.service.utils.SplineCSV;

public class PowerSource {
    private static final String SOURCE_PREFIX = "";

    public FunctionLL I;

    public PowerSource(String source) {
        switch(source) {
            default:
                I = new SplineCSV(SOURCE_PREFIX+"/solarIrradiance.csv", 500e-9, 1.5297e9, 0, 2);
                break;
        }
    }
}
