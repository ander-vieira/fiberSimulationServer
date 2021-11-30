package com.fibersim.fiberSimulationServer.core.components;

import com.fibersim.fiberSimulationServer.core.basics.Ray;

public interface Component {
    double intersectionPoint(Ray ray);
    void process(Ray ray, double ds);
}
