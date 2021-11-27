package com.fibersim.fiberSimulationServer.web.controller;

import com.fibersim.fiberSimulationServer.service.iterative.IterativeSim;
import com.fibersim.fiberSimulationServer.web.dto.SimulationResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class SimulationController {
    @GetMapping("/iterative")
    public SimulationResponseDTO iterativeSim(@RequestParam(name = "diameter") double diameter) {
        double[] P = IterativeSim.dyeIterative("Rh6G", 1.5e22, diameter, 0.98, 0.1);

        double lightP = Arrays.stream(P).sum();

        return new SimulationResponseDTO(lightP);
    }
}
