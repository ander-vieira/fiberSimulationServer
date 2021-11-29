package com.fibersim.fiberSimulationServer.service.components;

import com.fibersim.fiberSimulationServer.service.basics.Ray;
import com.fibersim.fiberSimulationServer.service.constraints.Constraint;
import com.fibersim.fiberSimulationServer.service.interphases.Interphase;
import com.fibersim.fiberSimulationServer.service.utils.Vector3;

public class Collector implements Component {
    private final Interphase interphase;
    Constraint constraint = Constraint.alwaysTrue;
    private final double[] finalPower;

    public Collector(Interphase interphase, double[] ll) {
        this.interphase = interphase;
        this.finalPower = new double[ll.length];
    }

    public Collector setConstraint(Constraint constraint) {
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
