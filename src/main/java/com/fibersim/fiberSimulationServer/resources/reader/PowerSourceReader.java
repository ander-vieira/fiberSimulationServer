package com.fibersim.fiberSimulationServer.resources.reader;

import com.fibersim.fiberSimulationServer.resources.resource.PowerSourceResource;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PowerSourceReader extends JsonDataReader<PowerSourceResource> {
    @Override
    protected String jsonDataFile() {
        return "powerSources";
    }

    @Override
    protected PowerSourceResource mapToObject(LinkedHashMap<String, Object> hashMap) {
        return objectMapper.convertValue(hashMap, PowerSourceResource.class);
    }

    public PowerSourceResource readSource(String powerSource) {
        List<PowerSourceResource> matchingParams = elementList.stream()
                .filter(params -> params.getSource().equals(powerSource))
                .collect(Collectors.toList());

        if(matchingParams.size() > 0) {
            return matchingParams.get(0);
        } else {
            return null;
        }
    }
}
