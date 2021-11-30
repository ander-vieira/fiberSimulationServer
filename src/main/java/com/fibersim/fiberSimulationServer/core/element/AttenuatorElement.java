package com.fibersim.fiberSimulationServer.core.element;

import com.fibersim.fiberSimulationServer.core.basics.Ray;
import com.fibersim.fiberSimulationServer.core.check.Check;
import com.fibersim.fiberSimulationServer.core.resources.Medium;

public class AttenuatorElement implements Element {
    double[] alpha;
    Check constraint = Check.alwaysTrue;

    public AttenuatorElement(Medium medium, double[] ll) {
        this.alpha = medium.attenuation.getArray(ll);
    }

    public AttenuatorElement setConstraint(Check constraint) {
        this.constraint = constraint;

        return this;
    }

    @Override
    public double intersectionPoint(Ray ray) {
        if(this.constraint.check(ray.pos, ray.vel) && alpha[ray.k] > 0) {
            return -Math.log(Math.random())/alpha[ray.k];
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    @Override
    public void process(Ray ray, double ds) {
        if(this.constraint.check(ray.pos, ray.vel)) {
            ray.kill();
        }
    }
}
