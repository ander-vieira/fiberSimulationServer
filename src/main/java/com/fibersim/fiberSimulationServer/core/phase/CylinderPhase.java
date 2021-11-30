package com.fibersim.fiberSimulationServer.core.phase;

import com.fibersim.fiberSimulationServer.core.util.Vector3;

public class CylinderPhase implements Phase {
    public double R2;
    public Vector3 origin;
    public Vector3 direction;

    public CylinderPhase(double R, Vector3 origin, Vector3 direction) {
        this.R2 = R*R;
        this.origin = origin;
        this.direction = direction;
    }

    @Override
    public double intersectionPoint(Vector3 pos, Vector3 vel) {
        Vector3 posR = pos.minus(origin);

        double posDotDir = posR.dot(direction);
        double velDotDir = vel.dot(direction);
        double posDotVel = posR.dot(vel)-posDotDir*velDotDir;
        double normPos2 = posR.norm2()-posDotDir*posDotDir;
        double normVel2 = vel.norm2()-velDotDir*velDotDir;

        if(normVel2 == 0) {
            return Double.POSITIVE_INFINITY;
        }

        double A = posDotVel/normVel2;
        double B = (normPos2-this.R2)/normVel2;
        double C = A*A-B;

        if(C <= 0) {
            return Double.POSITIVE_INFINITY;
        }

        double ds;

        if(B >= 0) {
            if(A >= 0) {
                ds = Double.POSITIVE_INFINITY;
            } else {
                ds = -Math.sqrt(C)-A;
            }
        } else {
            ds = Math.sqrt(C)-A;
        }

        if(ds <= 0) {
            ds = Double.POSITIVE_INFINITY;
        }

        return ds;
    }

    @Override
    public Vector3 getNormalVector(Vector3 pos) {
        Vector3 posR = pos.minus(origin);

        return posR.projectTangent(direction).normalize();
    }
}
