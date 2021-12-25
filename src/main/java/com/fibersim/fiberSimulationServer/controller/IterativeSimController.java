package com.fibersim.fiberSimulationServer.controller;

import com.fibersim.fiberSimulationServer.dto.iterative.IterativeSimParamsDTO;
import com.fibersim.fiberSimulationServer.dto.iterative.IterativeSimResponseDTO;
import com.fibersim.fiberSimulationServer.service.IterativeSimService;
import com.fibersim.fiberSimulationServer.view.ViewLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/iterative")
public class IterativeSimController {
    private final Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    IterativeSimService iterativeSimService;

    @Autowired
    ViewLoader viewLoader;

    /* ****** VIEWS ****** */

    @GetMapping("")
    public String iterative(Model model) {
        log.info("Processing view iterative");

        viewLoader.loadMainLayout(model);

        return "iterative";
    }

    /* ****** REST ENDPOINTS ****** */

    @PostMapping("")
    @ResponseBody
    public IterativeSimResponseDTO iterativeSim(@RequestBody IterativeSimParamsDTO paramsDTO) {
        log.info("Processing POST request /iterative");

        return iterativeSimService.dyeIterative(paramsDTO);
    }
}
