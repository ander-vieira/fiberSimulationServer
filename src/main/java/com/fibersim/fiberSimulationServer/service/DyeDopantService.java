package com.fibersim.fiberSimulationServer.service;

import com.fibersim.fiberSimulationServer.core.util.LambdaRange;
import com.fibersim.fiberSimulationServer.dto.dyeDopant.DyeDopantDataDTO;
import com.fibersim.fiberSimulationServer.dto.dyeDopant.DyeDopantPlotDTO;
import com.fibersim.fiberSimulationServer.resources.reader.DyeDopantReader;
import com.fibersim.fiberSimulationServer.resources.resource.DyeDopantResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DyeDopantService {
    @Autowired
    DyeDopantReader dyeDopantReader;

    public DyeDopantDataDTO getDyeDopantData(String name) {
        return new DyeDopantDataDTO(dyeDopantReader.readDopant(name));
    }

    public DyeDopantPlotDTO plotDyeDopantSigmas(String name, int points) {
        DyeDopantResource dyeDopantResource = dyeDopantReader.readDopant(name);

        if(dyeDopantResource == null) {
            return null;
        }

        double[] ll = new LambdaRange(dyeDopantResource.getMinLambda(), dyeDopantResource.getMaxLambda()).getLL(points);

        return DyeDopantPlotDTO.builder()
                .lambda(ll)
                .sigmaAbs(dyeDopantResource.getSigmaabs().getArray(ll))
                .sigmaEmi(dyeDopantResource.getSigmaemi().getArray(ll))
                .build();
    }
}
