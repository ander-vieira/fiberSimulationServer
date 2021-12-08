package com.fibersim.fiberSimulationServer.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnitIntegralTest {
    @Test
    @Tag("UnitTest")
    public void unitIntegralTest() {
        UnitIntegrand integrand = value -> value;

        Assertions.assertEquals(0.5, UnitIntegral.integrate(integrand), 1e-6);
        Assertions.assertEquals(0.25, UnitIntegral.integrate(integrand, 0.5), 1e-6);
    }
}
