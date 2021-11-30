package com.fibersim.fiberSimulationServer.controller;

import com.fibersim.fiberSimulationServer.dto.IterativeSimParamsDTO;
import com.fibersim.fiberSimulationServer.dto.IterativeSimResponseDTO;
import com.fibersim.fiberSimulationServer.service.IterativeSimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IterativeSimController {
    @Autowired
    IterativeSimService iterativeSimService;

    @PostMapping("/iterative")
    public IterativeSimResponseDTO iterativeSim(@RequestBody IterativeSimParamsDTO paramsDTO) {
        return iterativeSimService.dyeIterative(paramsDTO);
    }
}
