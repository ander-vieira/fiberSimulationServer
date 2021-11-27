package com.fibersim.fiberSimulationServer.service.components;

import com.fibersim.fiberSimulationServer.service.basics.Ray;

public interface Component {
    double intersectionPoint(Ray ray);
    void process(Ray ray, double ds);
}
