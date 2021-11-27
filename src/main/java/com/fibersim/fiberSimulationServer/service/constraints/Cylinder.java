package com.fibersim.fiberSimulationServer.service.constraints;

import com.fibersim.fiberSimulationServer.service.basics.Vector3;

public class Cylinder implements Constraint {
    private final double R2;
    private final Vector3 origin;

    public Cylinder(double R) {
        this.R2 = R*R;
        this.origin = Vector3.O;
    }

    public Cylinder(double R, Vector3 origin) {
        this.R2 = R*R;
        this.origin = origin;
    }

    @Override
    public boolean check(Vector3 pos, Vector3 vel) {
        Vector3 posR = pos.minus(origin);

        return posR.x*posR.x+posR.y*posR.y < R2;
    }
}
