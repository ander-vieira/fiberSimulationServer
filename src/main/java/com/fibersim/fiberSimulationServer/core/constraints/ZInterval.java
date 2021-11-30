package com.fibersim.fiberSimulationServer.core.constraints;

import com.fibersim.fiberSimulationServer.core.utils.Vector3;

public class ZInterval implements Constraint {
    private final double Zmin, Zmax;

    public ZInterval(double Zmin, double Zmax) {
        this.Zmin = Zmin;
        this.Zmax = Zmax;
    }

    @Override
    public boolean check(Vector3 pos, Vector3 vel) {
        return (pos.z >= Zmin && pos.z <= Zmax);
    }
}