package com.fibersim.fiberSimulationServer.controller;

import com.fibersim.fiberSimulationServer.dto.iterative.IterativeSimParamsDTO;
import com.fibersim.fiberSimulationServer.dto.iterative.IterativeSimResponseDTO;
import com.fibersim.fiberSimulationServer.service.IterativeSimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("iterative")
public class IterativeSimController {
    @Autowired
    IterativeSimService iterativeSimService;

    @PostMapping("")
    public IterativeSimResponseDTO iterativeSim(@RequestBody IterativeSimParamsDTO paramsDTO) {
        return iterativeSimService.dyeIterative(paramsDTO);
    }
}
