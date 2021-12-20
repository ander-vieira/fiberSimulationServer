package com.fibersim.fiberSimulationServer.service;

import com.fibersim.fiberSimulationServer.core.util.LambdaRange;
import com.fibersim.fiberSimulationServer.dto.dyeDopant.DyeDopantDataDTO;
import com.fibersim.fiberSimulationServer.dto.dyeDopant.DyeDopantPlotDTO;
import com.fibersim.fiberSimulationServer.exception.MissingResourceException;
import com.fibersim.fiberSimulationServer.resources.reader.DyeDopantReader;
import com.fibersim.fiberSimulationServer.resources.resource.DyeDopantResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DyeDopantService {
    @Autowired
    DyeDopantReader dyeDopantReader;

    public List<String> listDyeDopants() {
        return dyeDopantReader.readAllDopants().stream()
                .map(DyeDopantResource::getDopant)
                .collect(Collectors.toList());
    }

    public List<DyeDopantDataDTO> listDyeDopantData() {
        return dyeDopantReader.readAllDopants().stream()
                .map(DyeDopantDataDTO::new)
                .collect(Collectors.toList());
    }

    public DyeDopantDataDTO getDyeDopantData(String name) throws MissingResourceException {
        return new DyeDopantDataDTO(dyeDopantReader.readDopant(name));
    }

    public DyeDopantPlotDTO plotDyeDopantSigmas(String name, int points) throws MissingResourceException {
        DyeDopantResource dyeDopantResource = dyeDopantReader.readDopant(name);

        double[] ll = new LambdaRange(dyeDopantResource.getMinLambda(), dyeDopantResource.getMaxLambda()).getLL(points);

        return DyeDopantPlotDTO.builder()
                .lambda(ll)
                .sigmaAbs(dyeDopantResource.getSigmaabs().getArray(ll))
                .sigmaEmi(dyeDopantResource.getSigmaemi().getArray(ll))
                .build();
    }
}
