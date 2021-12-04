package com.fibersim.fiberSimulationServer.resources.reader;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public abstract class JsonDataReader {
    private static final String JSON_PREFIX = "/json";

    protected List<Object> jsonDataList;

    @Autowired
    ObjectMapper objectMapper;

    protected abstract String jsonDataFile();

    @PostConstruct
    private void readJsonData() {
        Resource resource = new ClassPathResource(JSON_PREFIX+jsonDataFile());
        String jsonString = "";
        jsonDataList = null;

        try(Scanner scanner = new Scanner(resource.getInputStream()).useDelimiter("\\A")) {
            jsonString = scanner.hasNext() ? scanner.next() : "";
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            jsonDataList = objectMapper.readValue(jsonString, new TypeReference<>() {});
        } catch(JacksonException e) {
            e.printStackTrace();
        }
    }
}
