package com.fibersim.fiberSimulationServer.core.element;

import com.fibersim.fiberSimulationServer.core.basics.Ray;
import com.fibersim.fiberSimulationServer.core.check.Check;
import com.fibersim.fiberSimulationServer.core.phase.Phase;
import com.fibersim.fiberSimulationServer.core.util.Vector3;

public class MirrorElement implements Element {
    private final Phase interphase;
    Check constraint = Check.alwaysTrue;

    public MirrorElement(Phase interphase) {
        this.interphase = interphase;
    }

    public MirrorElement setConstraint(Check constraint) {
        this.constraint = constraint;

        return this;
    }

    @Override
    public double intersectionPoint(Ray ray) {
        return interphase.intersectionPoint(ray.pos, ray.vel);
    }

    @Override
    public void process(Ray ray, double ds) {
        if(this.constraint.check(ray.pos, ray.vel)) {
            Vector3 normalVector = interphase.getNormalVector(ray.pos);

            ray.vel = ray.vel.minus(ray.vel.projectNormal(normalVector).scale(2));
        }
    }
}
