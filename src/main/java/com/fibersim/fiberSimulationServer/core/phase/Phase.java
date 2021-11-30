package com.fibersim.fiberSimulationServer.core.phase;

import com.fibersim.fiberSimulationServer.core.util.Vector3;

public interface Phase {
    double intersectionPoint(Vector3 pos, Vector3 vel);
    Vector3 getNormalVector(Vector3 pos);
}
