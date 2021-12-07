package com.fibersim.fiberSimulationServer.resources.reader;

import com.fibersim.fiberSimulationServer.resources.resource.MediumResource;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MediumReader extends JsonDataReader<MediumResource> {
    @Override
    protected String jsonDataFile() {
        return "mediums";
    }

    @Override
    protected MediumResource mapToObject(LinkedHashMap<String, Object> hashMap) {
        return objectMapper.convertValue(hashMap, MediumResource.class);
    }

    public MediumResource readMedium(String medium) {
        List<MediumResource> matchingParams = elementList.stream()
                .filter(params -> params.getMedium().equals(medium))
                .collect(Collectors.toList());

        if(matchingParams.size() > 0) {
            return matchingParams.get(0);
        } else {
            return null;
        }
    }
}
