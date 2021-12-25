package com.fibersim.fiberSimulationServer.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class ViewLoader {

    @Autowired
    private Environment env;

    public void loadMainLayout(Model model) {

        model.addAttribute("appName", env.getProperty("application.name"));
    }
}
