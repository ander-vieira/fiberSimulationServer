package com.fibersim.fiberSimulationServer.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ViewController {
    private final Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private Environment env;

    @GetMapping("")
    public String mainPage(Model model) {
        log.info("Processing view mainPage");

        loadMainLayout(model);

        return "mainPage";
    }
    @GetMapping("/iterative")
    public String iterative(Model model) {
        log.info("Processing view iterative");

        loadMainLayout(model);

        return "iterative";
    }

    private void loadMainLayout(Model model) {
        model.addAttribute("appName", env.getProperty("application.name"));
    }
}
