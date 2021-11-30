package com.fibersim.fiberSimulationServer.core.constraints;

import com.fibersim.fiberSimulationServer.core.utils.Vector3;

public interface Constraint {
    boolean check(Vector3 pos, Vector3 vel);

    Constraint alwaysTrue = (pos, vel) -> true;
}
