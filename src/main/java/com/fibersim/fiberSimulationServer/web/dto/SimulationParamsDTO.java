package com.fibersim.fiberSimulationServer.web.dto;

public class SimulationParamsDTO {
    private String dopant;
    private double concentration;
    private double diameter;
    private double length;

    public String getDopant() {
        return dopant;
    }

    public void setDopant(String dopant) {
        this.dopant = dopant;
    }

    public double getConcentration() {
        return concentration;
    }

    public void setConcentration(double concentration) {
        this.concentration = concentration;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
