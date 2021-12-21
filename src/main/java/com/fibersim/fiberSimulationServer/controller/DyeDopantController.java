package com.fibersim.fiberSimulationServer.controller;

import com.fibersim.fiberSimulationServer.dto.dyeDopant.DyeDopantDataDTO;
import com.fibersim.fiberSimulationServer.dto.dyeDopant.DyeDopantPlotDTO;
import com.fibersim.fiberSimulationServer.exception.MissingResourceException;
import com.fibersim.fiberSimulationServer.service.DyeDopantService;
import com.fibersim.fiberSimulationServer.view.ViewController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/dyeDopant")
public class DyeDopantController {
    private final Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    DyeDopantService dyeDopantService;

    @GetMapping("")
    public List<String> listDyeDopants() {
        log.info("Processing GET request /dyeDopant");

        return dyeDopantService.listDyeDopants();
    }

    @GetMapping("/all")
    public List<DyeDopantDataDTO> listDyeDopantData() {
        log.info("Processing GET request /dyeDopant/all");

        return dyeDopantService.listDyeDopantData();
    }

    @GetMapping("/{name}")
    public DyeDopantDataDTO getDyeDopantData(@PathVariable String name) {
        log.info("Processing GET request /dyeDopant/"+name);

        try {
            return dyeDopantService.getDyeDopantData(name);
        } catch(MissingResourceException e) {
            log.error("Dye dopant "+name+" not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dopant not found");
        }
    }

    @GetMapping("/{name}/plotSigmas")
    public DyeDopantPlotDTO plotDyeDopantSigmas(@PathVariable String name, @RequestParam(required = false, defaultValue = "101") Integer points) {
        log.info("Processing GET request /dyeDopant/"+name+"/plotSigmas");

        try {
            return dyeDopantService.plotDyeDopantSigmas(name, points);
        } catch(MissingResourceException e) {
            log.error("Dye dopant "+name+" not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dopant not found");
        }
    }
}
