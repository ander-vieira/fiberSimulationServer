package com.fibersim.fiberSimulationServer.resources;

import com.fibersim.fiberSimulationServer.resources.dto.DyeDopantDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DyeDopantReader extends JsonDataReader {
    @Override
    protected String jsonDataFile() {
        return "/dyeDopants.json";
    }

    public DyeDopantDTO readDopant(String dopant) {
        List<DyeDopantDTO> matchingParams = jsonDataList.stream()
                .map(hashMap -> objectMapper.convertValue(hashMap, DyeDopantDTO.class))
                .filter(params -> params.getDopant().equals(dopant))
                .collect(Collectors.toList());

        if(matchingParams.size() > 0) {
            return matchingParams.get(0);
        } else {
            return null;
        }
    }
}
