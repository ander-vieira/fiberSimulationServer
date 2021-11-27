package com.fibersim.fiberSimulationServer.service.resources;

public class PowerSource {
    public FunctionLL I;

    public static final String SOURCE_PREFIX = "/csv/";

    public PowerSource(String source) {
        switch(source) {
            default:
                I = new SplineCSV(SOURCE_PREFIX+"solarIrradiance.csv", 500e-9, 1.5297e9, 0, 2);
                break;
        }
    }
}
