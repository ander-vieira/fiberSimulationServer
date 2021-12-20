package com.fibersim.fiberSimulationServer.controller;

import com.fibersim.fiberSimulationServer.dto.dyeDopant.DyeDopantDataDTO;
import com.fibersim.fiberSimulationServer.dto.dyeDopant.DyeDopantPlotDTO;
import com.fibersim.fiberSimulationServer.exception.MissingResourceException;
import com.fibersim.fiberSimulationServer.service.DyeDopantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/dyeDopant")
public class DyeDopantController {
    @Autowired
    DyeDopantService dyeDopantService;

    @GetMapping("")
    public List<String> listDyeDopants() {
        return dyeDopantService.listDyeDopants();
    }

    @GetMapping("/all")
    public List<DyeDopantDataDTO> listDyeDopantData() {
        return dyeDopantService.listDyeDopantData();
    }

    @GetMapping("/{name}")
    public DyeDopantDataDTO getDyeDopantData(@PathVariable String name) {
        try {
            return dyeDopantService.getDyeDopantData(name);
        } catch(MissingResourceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dopant not found");
        }
    }

    @GetMapping("/{name}/plotSigmas")
    public DyeDopantPlotDTO plotDyeDopantSigmas(@PathVariable String name, @RequestParam(required = false, defaultValue = "101") Integer points) {
        try {
            return dyeDopantService.plotDyeDopantSigmas(name, points);
        } catch(MissingResourceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dopant not found");
        }
    }
}
