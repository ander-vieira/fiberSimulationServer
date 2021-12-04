package com.fibersim.fiberSimulationServer.resources.reader;

import com.fibersim.fiberSimulationServer.resources.resource.PowerSourceResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PowerSourceReader extends JsonDataReader {
    @Override
    protected String jsonDataFile() {
        return "/powerSources.json";
    }

    public PowerSourceResource readSource(String powerSource) {
        List<PowerSourceResource> matchingParams = jsonDataList.stream()
                .map(hashMap -> objectMapper.convertValue(hashMap, PowerSourceResource.class))
                .filter(params -> params.getSource().equals(powerSource))
                .collect(Collectors.toList());

        if(matchingParams.size() > 0) {
            return matchingParams.get(0);
        } else {
            return null;
        }
    }
}
