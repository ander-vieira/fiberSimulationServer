package com.fibersim.fiberSimulationServer.service.interphases;

import com.fibersim.fiberSimulationServer.service.utils.Vector3;

public interface Interphase {
    double intersectionPoint(Vector3 pos, Vector3 vel);
    Vector3 getNormalVector(Vector3 pos);
}
