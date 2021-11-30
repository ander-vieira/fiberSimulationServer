package com.fibersim.fiberSimulationServer.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SimulationTimerTest {
    @Autowired
    SimulationTimer simulationTimer;

    @Test
    void simulationTimerTest() {
        try {
            simulationTimer.startTimer();
            Thread.sleep(1000);

            Assertions.assertTrue(simulationTimer.getTime() >= 1);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }
}
