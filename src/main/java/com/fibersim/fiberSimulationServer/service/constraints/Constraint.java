package com.fibersim.fiberSimulationServer.service.constraints;

import com.fibersim.fiberSimulationServer.service.utils.Vector3;

public interface Constraint {
    boolean check(Vector3 pos, Vector3 vel);

    Constraint alwaysTrue = (pos, vel) -> true;
}
