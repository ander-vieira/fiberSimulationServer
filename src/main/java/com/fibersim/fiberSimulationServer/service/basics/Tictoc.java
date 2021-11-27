package com.fibersim.fiberSimulationServer.service.basics;

public class Tictoc {
    private long time;

    public Tictoc() {
        tic();
    }

    public void tic() {
        time = System.currentTimeMillis();
    }

    public double toc() {
        return (System.currentTimeMillis()-time)/1000.0;
    }
}
