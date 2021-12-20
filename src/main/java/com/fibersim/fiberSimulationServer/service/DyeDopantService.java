package com.fibersim.fiberSimulationServer.service;

import com.fibersim.fiberSimulationServer.core.util.LambdaRange;
import com.fibersim.fiberSimulationServer.dto.dyeDopant.DyeDopantDataDTO;
import com.fibersim.fiberSimulationServer.dto.dyeDopant.DyeDopantPlotDTO;
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
        List<DyeDopantResource> dyeDopantResourceList = dyeDopantReader.readAllDopants();

        return dyeDopantResourceList.stream().map(DyeDopantResource::getDopant).collect(Collectors.toList());
    }

    public List<DyeDopantDataDTO> listDyeDopantData() {
        List<DyeDopantResource> dyeDopantResourceList = dyeDopantReader.readAllDopants();

        return dyeDopantResourceList.stream().map(DyeDopantDataDTO::new).collect(Collectors.toList());
    }

    public DyeDopantDataDTO getDyeDopantData(String name) {
        DyeDopantResource dyeDopantResource = dyeDopantReader.readDopant(name);

        if(dyeDopantResource == null) {
            return null;
        }

        return new DyeDopantDataDTO(dyeDopantResource);
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
