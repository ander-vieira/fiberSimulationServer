package com.fibersim.fiberSimulationServer.service;

import com.fibersim.fiberSimulationServer.dto.DyeDopantParamsDTO;
import com.fibersim.fiberSimulationServer.dto.iterative.IterativeSimParamsDTO;
import com.fibersim.fiberSimulationServer.dto.iterative.IterativeSimResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class IterativeSimServiceTest {
    @Autowired
    IterativeSimService iterativeSimService;

    @Test
    void iterativeSimServiceTest() {
        List<DyeDopantParamsDTO> dyeDopantParams = new ArrayList<>();
        dyeDopantParams.add(DyeDopantParamsDTO.builder()
                .dopant("Rh6G")
                .concentration(1.5e22)
                .build());

        IterativeSimParamsDTO params =
                IterativeSimParamsDTO.builder()
                .dyeDopants(dyeDopantParams)
                .diameter(1e-3)
                .cladRatio(0.98)
                .length(0.001)
                .darkLength(0)
                .build();

        Assertions.assertNotNull(params);

        IterativeSimResponseDTO response =
                iterativeSimService.dyeIterative(params);

        Assertions.assertNotNull(response);
    }
}
