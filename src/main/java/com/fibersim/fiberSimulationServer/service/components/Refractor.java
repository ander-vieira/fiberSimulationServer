package com.fibersim.fiberSimulationServer.service.components;

import com.fibersim.fiberSimulationServer.service.basics.Misc;
import com.fibersim.fiberSimulationServer.service.basics.Ray;
import com.fibersim.fiberSimulationServer.service.utils.Vector3;
import com.fibersim.fiberSimulationServer.service.constraints.Constraint;
import com.fibersim.fiberSimulationServer.service.interphases.Interphase;
import com.fibersim.fiberSimulationServer.service.resources.Medium;

public class Refractor implements Component {
    private final Interphase interphase;
    private final double[] Nplus;
    private final double[] Nminus;
    Constraint constraint = Constraint.alwaysTrue;

    public Refractor(Interphase interphase, Medium outMedium, Medium inMedium, double[] ll) {
        this.interphase = interphase;
        this.Nplus = outMedium.refractionIndex.getArray(ll);
        this.Nminus = inMedium.refractionIndex.getArray(ll);
    }

    public Refractor setConstraint(Constraint constraint) {
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

            Vector3 reflectedVel = ray.vel.minus(ray.vel.projectNormal(normalVector).scale(2));

            double cosTheta = ray.vel.dot(normalVector);

            double N1, N2;
            if(cosTheta > 0) {
                N1 = Nminus[ray.k];
                N2 = Nplus[ray.k];
            } else {
                N1 = Nplus[ray.k];
                N2 = Nminus[ray.k];
            }

            if(N2 < N1 && cosTheta < Math.sqrt(1-N2*N2/(N1*N1))) {
                ray.vel = reflectedVel;
            } else {
                double newCosTheta = Math.copySign(Math.sqrt(1-(1-cosTheta*cosTheta)*(N1*N1)/(N2*N2)), cosTheta);

                double fresnelR = Misc.fresnelR(cosTheta, newCosTheta, N1, N2);

                if(Math.random() < fresnelR) {
                    ray.vel = reflectedVel;
                } else {
                    Vector3 tangentVel = ray.vel.minus(normalVector.scale(cosTheta));
                    Vector3 newTangentVel = tangentVel.scale(N1/N2);
                    Vector3 newNormalVel = normalVector.scale(newCosTheta);

                    ray.vel = newTangentVel.plus(newNormalVel);
                }
            }
        }
    }
}
