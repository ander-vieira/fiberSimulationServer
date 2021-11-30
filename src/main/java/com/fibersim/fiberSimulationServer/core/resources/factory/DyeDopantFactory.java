package com.fibersim.fiberSimulationServer.core.resources.factory;

import com.fibersim.fiberSimulationServer.core.resources.DyeDopant;
import com.fibersim.fiberSimulationServer.resources.CSVInterpolator;
import com.fibersim.fiberSimulationServer.resources.DyeDopantReader;
import com.fibersim.fiberSimulationServer.resources.dto.DyeDopantParamsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DyeDopantFactory {
    @Autowired
    DyeDopantReader dyeDopantReader;

    public DyeDopant make(String dopant) {
        DyeDopantParamsDTO dyeDopantParamsDTO = dyeDopantReader.readDopant(dopant);
        DyeDopant dyeDopant = new DyeDopant();

        dyeDopant.tauRad = dyeDopantParamsDTO.getTauRad();
        dyeDopant.tauNR = dyeDopantParamsDTO.getTauNR();
        dyeDopant.minLambda = dyeDopantParamsDTO.getMinLambda();
        dyeDopant.maxLambda = dyeDopantParamsDTO.getMaxLambda();
        dyeDopant.sigmaabs = new CSVInterpolator(dyeDopantParamsDTO.getSigmaabs());
        dyeDopant.sigmaemi = new CSVInterpolator(dyeDopantParamsDTO.getSigmaemi());

        return dyeDopant;
    }
}
