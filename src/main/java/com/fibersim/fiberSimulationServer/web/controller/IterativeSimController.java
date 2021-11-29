package com.fibersim.fiberSimulationServer.web.controller;

import com.fibersim.fiberSimulationServer.dto.IterativeSimParamsDTO;
import com.fibersim.fiberSimulationServer.dto.IterativeSimResponseDTO;
import com.fibersim.fiberSimulationServer.service.iterative.IterativeSim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class IterativeSimController {
    @Autowired
    IterativeSim iterativeSim;

    @PostMapping("/iterative")
    public IterativeSimResponseDTO iterativeSim(@RequestBody IterativeSimParamsDTO paramsDTO) {
        double[] P = iterativeSim.dyeIterative("Rh6G", paramsDTO.getConcentration(), paramsDTO.getDiameter(), 0.98, paramsDTO.getLength());

        double lightP = Arrays.stream(P).sum();

        return IterativeSimResponseDTO.builder()
                .lightP(lightP)
                .build();
    }
}
