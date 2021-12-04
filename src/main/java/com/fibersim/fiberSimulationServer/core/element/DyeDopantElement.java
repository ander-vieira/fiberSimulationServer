package com.fibersim.fiberSimulationServer.core.element;

import com.fibersim.fiberSimulationServer.core.basics.Ray;
import com.fibersim.fiberSimulationServer.core.check.Check;
import com.fibersim.fiberSimulationServer.core.util.Vector3;
import com.fibersim.fiberSimulationServer.resources.resource.DyeDopantResource;

public class DyeDopantElement implements Element {
    private final double N;
    private final double quantumYield;
    private final double[] ll;
    private final double[] sigmaabs;
    private final double[] sigmaemi;
    private final double sumEmi;
    Check check = Check.alwaysTrue;

    public DyeDopantElement(DyeDopantResource dopant, double N, double[] ll) {
        this.N = N;
        this.quantumYield = dopant.getTauNR()/(dopant.getTauRad()+dopant.getTauNR());
        this.ll = ll;
        this.sigmaabs = dopant.getSigmaabs().getArray(ll);
        this.sigmaemi = dopant.getSigmaemi().getArray(ll);

        double sumEmi = 0;
        for(double sigma : sigmaemi) sumEmi += sigma;
        this.sumEmi = sumEmi;
    }

    @Override
    public double intersectionPoint(Ray ray) {
        double alpha = sigmaabs[ray.k]*N;
        if(this.check.check(ray.pos, ray.vel) && alpha > 0) {
            return -Math.log(Math.random())/alpha;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    @Override
    public void process(Ray ray, double ds) {
        if(this.check.check(ray.pos, ray.vel)) {
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
