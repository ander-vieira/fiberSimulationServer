package com.fibersim.fiberSimulationServer.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ViewController {
    @Autowired
    private Environment env;

    @GetMapping("")
    public String mainPage(Model model) {
        model.addAttribute("appName", env.getProperty("application.name"));

        return "mainPage";
    }
    @GetMapping("/iterative")
    public String iterativeSim(Model model) {
        model.addAttribute("appName", env.getProperty("application.name"));

        return "iterative";
    }
}
