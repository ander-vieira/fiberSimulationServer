package com.fibersim.fiberSimulationServer.web.dto;

public class SimulationParamsDTO {
    private String dopant;
    private double N;
    private double diameter;
    private double L;

    public String getDopant() {
        return dopant;
    }

    public void setDopant(String dopant) {
        this.dopant = dopant;
    }

    public double getN() {
        return N;
    }

    public void setN(double n) {
        N = n;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getL() {
        return L;
    }

    public void setL(double l) {
        L = l;
    }
}
