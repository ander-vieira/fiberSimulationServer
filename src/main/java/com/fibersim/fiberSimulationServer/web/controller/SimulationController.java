package com.fibersim.fiberSimulationServer.web.controller;

import com.fibersim.fiberSimulationServer.service.iterative.IterativeSim;
import com.fibersim.fiberSimulationServer.web.dto.SimulationParamsDTO;
import com.fibersim.fiberSimulationServer.web.dto.SimulationResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class SimulationController {
    @PostMapping("/iterative")
    public SimulationResponseDTO iterativeSim(@RequestBody SimulationParamsDTO paramsDTO) {
        double[] P = IterativeSim.dyeIterative("Rh6G", paramsDTO.getConcentration(), paramsDTO.getDiameter(), 0.98, paramsDTO.getLength());

        double lightP = Arrays.stream(P).sum();

        return new SimulationResponseDTO(lightP);
    }
}
