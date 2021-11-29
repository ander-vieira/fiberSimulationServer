package com.fibersim.fiberSimulationServer.service.components;

import com.fibersim.fiberSimulationServer.service.basics.Ray;
import com.fibersim.fiberSimulationServer.service.utils.Vector3;
import com.fibersim.fiberSimulationServer.service.constraints.Constraint;
import com.fibersim.fiberSimulationServer.service.interphases.Interphase;

public class Mirror implements Component {
    private final Interphase interphase;
    Constraint constraint = Constraint.alwaysTrue;

    public Mirror(Interphase interphase) {
        this.interphase = interphase;
    }

    public Mirror setConstraint(Constraint constraint) {
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
