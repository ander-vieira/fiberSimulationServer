package com.fibersim.fiberSimulationServer.resources;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MediumReader extends JsonDataReader {
    @Override
    protected String jsonDataFile() {
        return "/mediums.json";
    }

    public MediumResource readMedium(String medium) {
        List<MediumResource> matchingParams = jsonDataList.stream()
                .map(hashMap -> objectMapper.convertValue(hashMap, MediumResource.class))
                .filter(params -> params.getMedium().equals(medium))
                .collect(Collectors.toList());

        if(matchingParams.size() > 0) {
            return matchingParams.get(0);
        } else {
            return null;
        }
    }
}
