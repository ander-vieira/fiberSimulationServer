package com.fibersim.fiberSimulationServer.service;

import com.fibersim.fiberSimulationServer.core.utils.SplineCSV;
import com.fibersim.fiberSimulationServer.dto.IterativeSimParamsDTO;
import com.fibersim.fiberSimulationServer.dto.IterativeSimResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IterativeSimServiceTest {
    @Autowired
    IterativeSimService iterativeSimService;

    @Test
    void iterativeSimServiceTest() {
        IterativeSimParamsDTO params =
                IterativeSimParamsDTO.builder()
                .dopant("Rh6G")
                .concentration(1.5e22)
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
