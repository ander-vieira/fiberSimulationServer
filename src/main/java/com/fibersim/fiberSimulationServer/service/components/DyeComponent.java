package com.fibersim.fiberSimulationServer.service.components;

import com.fibersim.fiberSimulationServer.service.basics.Ray;
import com.fibersim.fiberSimulationServer.service.basics.Vector3;
import com.fibersim.fiberSimulationServer.service.constraints.Constraint;
import com.fibersim.fiberSimulationServer.service.resources.DyeDopant;

public class DyeComponent implements Component {
    private final double N;
    private final double quantumYield;
    private final double[] ll;
    private final double[] sigmaabs;
    private final double[] sigmaemi;
    private final double sumEmi;
    Constraint constraint = Constraint.alwaysTrue;

    public DyeComponent(DyeDopant dopant, double N, double[] ll) {
        this.N = N;
        this.quantumYield = dopant.tauNR/(dopant.tauRad+dopant.tauNR);
        this.ll = ll;
        this.sigmaabs = dopant.sigmaabs.getArray(ll);
        this.sigmaemi = dopant.sigmaemi.getArray(ll);

        double sumEmi = 0;
        for(double sigma : sigmaemi) sumEmi += sigma;
        this.sumEmi = sumEmi;
    }

    @Override
    public double intersectionPoint(Ray ray) {
        double alpha = sigmaabs[ray.k]*N;
        if(this.constraint.check(ray.pos, ray.vel) && alpha > 0) {
            return -Math.log(Math.random())/alpha;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    @Override
    public void process(Ray ray, double ds) {
        if(this.constraint.check(ray.pos, ray.vel)) {
            if(Math.random() < quantumYield) {
                double rand1 = 2 * Math.PI * Math.random();
                double rand2 = 2 * Math.random() - 1;
                double rand22 = Math.sqrt(1 - rand2 * rand2);
                ray.vel = new Vector3(rand22 * Math.cos(rand1), rand22 * Math.sin(rand1), rand2);

                int oldK = ray.k;
                ray.k = generateLambda();

                ray.power *= ll[ray.k] / ll[oldK];
            } else {
                ray.kill();
            }
        }
    }

    private int generateLambda() {
        double rand1 = Math.random()*this.sumEmi;
        double partialSum = 0;

        for(int k = 0 ; k < sigmaemi.length ; k++) {
            partialSum += sigmaemi[k];
            if(partialSum > rand1) return k;
        }

        return -1;
    }
}
