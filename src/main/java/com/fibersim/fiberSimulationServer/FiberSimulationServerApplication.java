package com.fibersim.fiberSimulationServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fibersim.fiberSimulationServer")
public class FiberSimulationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiberSimulationServerApplication.class, args);
	}
}
