package com.fibersim.fiberSimulationServer.core.check;

import com.fibersim.fiberSimulationServer.core.util.Vector3;

public interface Check {
    boolean check(Vector3 pos, Vector3 vel);

    Check alwaysTrue = (pos, vel) -> true;
}
