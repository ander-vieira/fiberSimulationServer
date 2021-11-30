package com.fibersim.fiberSimulationServer.resources;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fibersim.fiberSimulationServer.resources.dto.DyeDopantParamsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class DyeDopantReader {
    private static final String DYE_DOPANTS_FILE = "/json/dyeDopants.json";

    private List<DyeDopantParamsDTO> dyeDopantParamsDTOs;
    @Autowired
    ObjectMapper objectMapper;

    @PostConstruct
    private void readDyeDopants() {
        Resource resource = new ClassPathResource(DYE_DOPANTS_FILE);
        String jsonString = "";
        dyeDopantParamsDTOs = null;

        try(Scanner scanner = new Scanner(resource.getInputStream()).useDelimiter("\\A")) {
            jsonString = scanner.hasNext() ? scanner.next() : "";
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            dyeDopantParamsDTOs = objectMapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch(JacksonException e) {
            e.printStackTrace();
        }
    }

    public DyeDopantParamsDTO readDopant(String dopant) {
        List<DyeDopantParamsDTO> matchingParams = dyeDopantParamsDTOs.stream().filter(params -> params.getDopant().equals(dopant)).collect(Collectors.toList());

        if(matchingParams.size() > 0) {
            return matchingParams.get(0);
        } else {
            return null;
        }
    }
}
