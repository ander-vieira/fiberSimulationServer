package com.fibersim.fiberSimulationServer.core.check;

import com.fibersim.fiberSimulationServer.core.util.Vector3;

public class ZIntervalCheck implements Check {
    private final double Zmin, Zmax;

    public ZIntervalCheck(double Zmin, double Zmax) {
        this.Zmin = Zmin;
        this.Zmax = Zmax;
    }

    @Override
    public boolean check(Vector3 pos, Vector3 vel) {
        return (pos.z >= Zmin && pos.z <= Zmax);
    }
}
