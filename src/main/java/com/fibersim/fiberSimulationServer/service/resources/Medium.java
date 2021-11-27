package com.fibersim.fiberSimulationServer.service.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class Medium {
    public FunctionLL refractionIndex;
    public FunctionLL attenuation;

    //public static final String MEDIUM_PREFIX = System.getProperty("user.dir")+"/src/main/resources/csv/";
    public static final String MEDIUM_PREFIX = "/csv/";
    //public static final String MEDIUM_PREFIX = ClassLoader.getSystemClassLoader().getResource(".").getPath()+"/src/main/resources/csv/";

    public Medium(String medium) {
        if(medium.equals("clad")) {
            refractionIndex = FunctionLL.constant(1.4);
            attenuation = new SplineCSV(MEDIUM_PREFIX+"attenuationPMMA.csv", 500e-9, 0.01842*0.5);
        } else if(medium.equals("PMMA")) {
            refractionIndex = new FunctionLL() {
                @Override
                public double eval(double lambda) {
                    return Math.sqrt(1/(-8.226e-15/(lambda*lambda)+0.8393)+1);
                }
            };
            attenuation = new SplineCSV(MEDIUM_PREFIX+"attenuationPMMA.csv", 500e-9, 0.01842);
        } else {
            refractionIndex = FunctionLL.constant(1);
            attenuation = FunctionLL.constant(0);
        }
    }
}
