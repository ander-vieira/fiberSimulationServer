package com.fibersim.fiberSimulationServer.service.interphases;

import com.fibersim.fiberSimulationServer.service.basics.Vector3;

public class PlaneInterphase implements Interphase {
    Vector3 normalVector;
    Vector3 origin;

    public PlaneInterphase(Vector3 normalVector, Vector3 origin) {
        this.normalVector = normalVector;
        this.origin = origin;
    }

    @Override
    public double intersectionPoint(Vector3 pos, Vector3 vel) {
        Vector3 posR = pos.minus(origin);

        double a = vel.dot(normalVector);
        double b = posR.dot(normalVector);
        double ds;

        if(a == 0) {
            ds = Double.POSITIVE_INFINITY;
        } else {
            ds = -b/a;
        }

        if(ds <= 0) {
            ds = Double.POSITIVE_INFINITY;
        }

        return ds;
    }

    @Override
    public Vector3 getNormalVector(Vector3 pos) {
        return normalVector;
    }
}
