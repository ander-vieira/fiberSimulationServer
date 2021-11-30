package com.fibersim.fiberSimulationServer.core.util;

public class Vector3 {
    public double x;
    public double y;
    public double z;

    public static final Vector3 O = new Vector3(0, 0, 0);
    public static final Vector3 X = new Vector3(1, 0, 0);
    public static final Vector3 Y = new Vector3(0, 1, 0);
    public static final Vector3 Z = new Vector3(0, 0, 1);

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double dot(Vector3 vec2) {
        return x*vec2.x+y*vec2.y+z*vec2.z;
    }

    public double norm2() {
        return x*x+y*y+z*z;
    }

    public double norm() {
        return Math.sqrt(this.norm2());
    }

    public Vector3 normalize() {
        double norm = this.norm();

        if(norm == 0) {
            return new Vector3(0, 0, 0);
        } else {
            return new Vector3(x/norm, y/norm, z/norm);
        }
    }

    public Vector3 projectNormal(Vector3 n) {
        return n.scale(this.dot(n));
    }

    public Vector3 projectTangent(Vector3 n) {
        return this.minus(this.projectNormal(n));
    }

    public Vector3 plus(Vector3 vec2) {
        return new Vector3(x+vec2.x, y+vec2.y, z+vec2.z);
    }

    public Vector3 minus(Vector3 vec2) {
        return new Vector3(x-vec2.x, y-vec2.y, z-vec2.z);
    }

    public Vector3 scale(double a) {
        return new Vector3(a * x, a * y, a * z);
    }

    public String toString() {
        return "("+x+","+y+","+z+")";
    }
}
