package com.fibersim.fiberSimulationServer.controller;

import com.fibersim.fiberSimulationServer.view.ViewLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ViewLoader viewLoader;

    @GetMapping("")
    public String mainPage(Model model) {
        log.info("Processing view mainPage");

        viewLoader.loadMainLayout(model);

        return "index";
    }
}
