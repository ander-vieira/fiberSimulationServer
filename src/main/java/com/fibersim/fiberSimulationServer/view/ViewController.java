package com.fibersim.fiberSimulationServer.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ViewController {
    @GetMapping("")
    public String mainPage(Model model) {
        return "mainPage";
    }
    @GetMapping("/iterative")
    public String iterativeSim(Model model) {
        return "iterative";
    }
}
