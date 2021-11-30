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
            double sleepTime = Math.random();
            simulationTimer.startTimer();
            Thread.sleep((long)Math.ceil(sleepTime*1000));

            Assertions.assertTrue(simulationTimer.getTime() >= sleepTime);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }
}
