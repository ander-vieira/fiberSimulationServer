package com.fibersim.fiberSimulationServer.core.element;

import com.fibersim.fiberSimulationServer.core.basics.Ray;

public interface Element {
    double intersectionPoint(Ray ray);
    void process(Ray ray, double ds);
}
