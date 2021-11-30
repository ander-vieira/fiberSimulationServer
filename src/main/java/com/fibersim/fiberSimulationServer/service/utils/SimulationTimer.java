package com.fibersim.fiberSimulationServer.service.utils;

import org.springframework.stereotype.Component;

@Component
public class SimulationTimer {
    private long time;

    public void startTimer() {
        time = System.currentTimeMillis();
    }

    public double getTime() {
        return (System.currentTimeMillis()-time)/1000.0;
    }
}
