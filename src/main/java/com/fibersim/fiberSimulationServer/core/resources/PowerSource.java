package com.fibersim.fiberSimulationServer.core.resources;

import com.fibersim.fiberSimulationServer.core.util.FunctionLL;
import com.fibersim.fiberSimulationServer.resources.CSVInterpolator;

public class PowerSource {
    private static final String SOURCE_PREFIX = "";

    public FunctionLL I;

    public PowerSource(String source) {
        switch(source) {
            default:
                I = new CSVInterpolator(SOURCE_PREFIX+"/solarIrradiance.csv", 500e-9, 1.5297e9, 0, 2);
                break;
        }
    }
}
