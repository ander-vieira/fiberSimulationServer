package com.fibersim.fiberSimulationServer.web.dto;

public class SimulationResponseDTO {
    private double lightP;

    public SimulationResponseDTO(double lightP) {
        this.lightP = lightP;
    }

    public double getLightP() {
        return lightP;
    }

    public void setLightP(double lightP) {
        this.lightP = lightP;
    }
}
