package com.fibersim.fiberSimulationServer.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
public class SimulationTimerTest {
    @Autowired
    SimulationTimer simulationTimer;

    @Test
    @Tag("UnitTest")
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
