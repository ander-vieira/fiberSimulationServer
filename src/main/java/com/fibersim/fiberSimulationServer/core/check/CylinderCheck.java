package com.fibersim.fiberSimulationServer.core.check;

import com.fibersim.fiberSimulationServer.core.util.Vector3;

public class CylinderCheck implements Check {
    private final double R2;
    private final Vector3 origin;

    public CylinderCheck(double R) {
        this.R2 = R*R;
        this.origin = Vector3.O;
    }

    public CylinderCheck(double R, Vector3 origin) {
        this.R2 = R*R;
        this.origin = origin;
    }

    @Override
    public boolean check(Vector3 pos, Vector3 vel) {
        Vector3 posR = pos.minus(origin);

        return posR.x*posR.x+posR.y*posR.y < R2;
    }
}
