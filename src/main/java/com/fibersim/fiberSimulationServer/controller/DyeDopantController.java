package com.fibersim.fiberSimulationServer.controller;

import com.fibersim.fiberSimulationServer.dto.dyeDopant.DyeDopantDataDTO;
import com.fibersim.fiberSimulationServer.dto.view.PlotDTO;
import com.fibersim.fiberSimulationServer.exception.MissingResourceException;
import com.fibersim.fiberSimulationServer.service.DyeDopantService;
import com.fibersim.fiberSimulationServer.view.ViewLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/dyeDopant")
public class DyeDopantController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    DyeDopantService dyeDopantService;

    @Autowired
    ViewLoader viewLoader;

    /* ****** VIEWS ****** */

    @GetMapping("")
    public String dyeDopant(Model model) {
        log.info("Processing view dyeDopant");

        viewLoader.loadMainLayout(model);

        model.addAttribute("dyeDopants", dyeDopantService.listDyeDopantData());

        return "dyeDopant/dyeDopantList";
    }

    @GetMapping("/{name}")
    public String dyeDopant(Model model, @PathVariable String name) {
        log.info("Processing view dyeDopant/"+name);

        viewLoader.loadMainLayout(model);

        try {
            model.addAttribute("dyeDopant", dyeDopantService.getDyeDopantData(name));
            model.addAttribute("dyeDopantPlot", dyeDopantService.plotDyeDopantSigmas(name, 301));
        } catch(MissingResourceException e) {
            log.error("Dye dopant "+name+" not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dopant not found");
        }

        return "dyeDopant/dyeDopantDetails";
    }

    /* ****** REST ENDPOINTS ****** */

    @PostMapping("")
    @ResponseBody
    public List<DyeDopantDataDTO> listDyeDopantData() {
        log.info("Processing POST request /dyeDopant");

        return dyeDopantService.listDyeDopantData();
    }

    @PostMapping("/name")
    @ResponseBody
    public List<String> listDyeDopantNames() {
        log.info("Processing POST request /dyeDopant/name");

        return dyeDopantService.listDyeDopantNames();
    }

    @PostMapping("/{name}")
    @ResponseBody
    public DyeDopantDataDTO getDyeDopantData(@PathVariable String name) {
        log.info("Processing POST request /dyeDopant/"+name);

        try {
            return dyeDopantService.getDyeDopantData(name);
        } catch(MissingResourceException e) {
            log.error("Dye dopant "+name+" not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dopant not found");
        }
    }

    @PostMapping("/{name}/plotSigmas")
    @ResponseBody
    public PlotDTO plotDyeDopantSigmas(@PathVariable String name, @RequestParam(required = false, defaultValue = "101") Integer points) {
        log.info("Processing POST request /dyeDopant/"+name+"/plotSigmas");

        try {
            return dyeDopantService.plotDyeDopantSigmas(name, points);
        } catch(MissingResourceException e) {
            log.error("Dye dopant "+name+" not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dopant not found");
        }
    }
}
