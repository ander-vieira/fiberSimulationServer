package com.fibersim.fiberSimulationServer.core.interphases;

import com.fibersim.fiberSimulationServer.core.utils.Vector3;

public interface Interphase {
    double intersectionPoint(Vector3 pos, Vector3 vel);
    Vector3 getNormalVector(Vector3 pos);
}
