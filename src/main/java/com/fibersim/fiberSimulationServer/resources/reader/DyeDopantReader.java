package com.fibersim.fiberSimulationServer.resources.reader;

import com.fibersim.fiberSimulationServer.resources.resource.DyeDopantResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DyeDopantReader extends JsonDataReader {
    @Override
    protected String jsonDataFile() {
        return "dyeDopants";
    }

    public DyeDopantResource readDopant(String dopant) {
        List<DyeDopantResource> matchingParams = jsonDataList.stream()
                .map(hashMap -> objectMapper.convertValue(hashMap, DyeDopantResource.class))
                .filter(params -> params.getDopant().equals(dopant))
                .collect(Collectors.toList());

        if(matchingParams.size() > 0) {
            return matchingParams.get(0);
        } else {
            return null;
        }
    }
}
