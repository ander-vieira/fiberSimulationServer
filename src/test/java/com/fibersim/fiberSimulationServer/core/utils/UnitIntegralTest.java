package com.fibersim.fiberSimulationServer.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnitIntegralTest {
    @Autowired
    UnitIntegral unitIntegral;

    @Test
    public void unitIntegralTest() {
        UnitIntegrand integrand = value -> value;

        Assertions.assertEquals(0.5, unitIntegral.integrate(integrand), 1e-6);
        Assertions.assertEquals(0.25, unitIntegral.integrate(integrand, 0.5), 1e-6);
    }
}
