package com.fibersim.fiberSimulationServer.core.basics;

import com.fibersim.fiberSimulationServer.core.utils.Vector3;

public class Ray {
    public Vector3 pos;
    public Vector3 vel;
    public double power;
    public int k;
    private boolean loopOn;

    public Ray(Vector3 pos, Vector3 vel, double power, int k) {
        this.pos = pos;
        this.vel = vel;
        this.power = power;
        this.k = k;
        this.loopOn = true;
    }

    public void move(double ds) {
        pos.x += vel.x*ds;
        pos.y += vel.y*ds;
        pos.z += vel.z*ds;
    }

    public boolean alive() {
        return this.loopOn;
    }

    public void kill() {
        this.loopOn = false;
    }
}
