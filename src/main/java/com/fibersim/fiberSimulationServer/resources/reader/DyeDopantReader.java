package com.fibersim.fiberSimulationServer.resources.reader;

import com.fibersim.fiberSimulationServer.dto.DyeDopantDTO;
import com.fibersim.fiberSimulationServer.dto.DyeDopantParamsDTO;
import com.fibersim.fiberSimulationServer.resources.resource.DyeDopantResource;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DyeDopantReader extends JsonDataReader<DyeDopantResource> {
    @Override
    protected String jsonDataFile() {
        return "dyeDopants";
    }

    @Override
    protected DyeDopantResource mapToObject(LinkedHashMap<String, Object> hashMap) {
        return objectMapper.convertValue(hashMap, DyeDopantResource.class);
    }

    public DyeDopantResource readDopant(String dopant) {
        List<DyeDopantResource> matchingParams = elementList.stream()
                .filter(params -> params.getDopant().equals(dopant))
                .collect(Collectors.toList());

        if(matchingParams.size() > 0) {
            return matchingParams.get(0);
        } else {
            return null;
        }
    }

    public List<DyeDopantDTO> readDopants(List<DyeDopantParamsDTO> dyeDopantParamsDTO) {
        return dyeDopantParamsDTO.stream().map(params -> DyeDopantDTO.builder()
                .dyeDopant(readDopant(params.getDopant()))
                .concentration(params.getConcentration())
                .build()).collect(Collectors.toList());
    }

    public List<DyeDopantResource> readAllDopants() {
        return elementList;
    }
}
