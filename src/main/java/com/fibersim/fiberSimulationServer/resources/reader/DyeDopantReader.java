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

    public DyeDopantDTO readDopant(DyeDopantParamsDTO dyeDopantParamsDTO) {
        List<DyeDopantResource> matchingParams = elementList.stream()
                .filter(params -> params.getDopant().equals(dyeDopantParamsDTO.getDopant()))
                .collect(Collectors.toList());

        if(matchingParams.size() > 0) {
            return DyeDopantDTO.builder()
                    .dyeDopant(matchingParams.get(0))
                    .concentration(dyeDopantParamsDTO.getConcentration())
                    .build();
        } else {
            return null;
        }
    }
}
