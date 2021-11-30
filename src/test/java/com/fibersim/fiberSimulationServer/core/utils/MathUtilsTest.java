package com.fibersim.fiberSimulationServer.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MathUtilsTest {
    @Test
    public void mathUtilsTest() {
        Assertions.assertEquals(0, MathUtils.fresnelR(1, 1, 1, 1), 1e-12);
        Assertions.assertEquals(0.04, MathUtils.fresnelR(1, 1, 1, 1.5), 1e-12);
    }
}
