package com.fibersim.fiberSimulationServer.core.element;

import com.fibersim.fiberSimulationServer.core.basics.Ray;
import com.fibersim.fiberSimulationServer.core.check.Check;
import com.fibersim.fiberSimulationServer.core.phase.Phase;
import com.fibersim.fiberSimulationServer.core.util.Vector3;

public class CollectorElement implements Element {
    private final Phase interphase;
    Check constraint = Check.alwaysTrue;
    private final double[] finalPower;

    public CollectorElement(Phase interphase, double[] ll) {
        this.interphase = interphase;
        this.finalPower = new double[ll.length];
    }

    public CollectorElement setConstraint(Check constraint) {
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

            if(ray.vel.dot(normalVector) > 0) {
                this.finalPower[ray.k] += ray.power;
                ray.kill();
            }
        }
    }

    public double getFinalPower(int k) {
        return finalPower[k];
    }

    public double getTotalPower() {
        double result = 0;

        for (double power : finalPower) result += power;

        return result;
    }
}
