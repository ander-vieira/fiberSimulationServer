package com.fibersim.fiberSimulationServer.core.constraints;

import com.fibersim.fiberSimulationServer.core.utils.Vector3;

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

        return posR.getX()*posR.getX()+posR.getY()*posR.getY() < R2;
    }
}
