package com.fibersim.fiberSimulationServer.service.components;

import com.fibersim.fiberSimulationServer.service.basics.Ray;
import com.fibersim.fiberSimulationServer.service.constraints.Constraint;
import com.fibersim.fiberSimulationServer.service.resources.Medium;

public class Attenuator implements Component {
    double[] alpha;
    Constraint constraint = Constraint.alwaysTrue;

    public Attenuator(Medium medium, double[] ll) {
        this.alpha = medium.attenuation.getArray(ll);
    }

    public Attenuator setConstraint(Constraint constraint) {
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
