package com.fibersim.fiberSimulationServer.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LambdaRangeTest {
    @Test
    @Tag("UnitTest")
    public void lambdaRangeTest() {
        LambdaRange lambdaRange = new LambdaRange();

        Assertions.assertEquals(0, lambdaRange.getDlambda(1), 1e-12);
        Assertions.assertEquals(0, lambdaRange.getDlambda(11), 1e-12);
        Assertions.assertNull(lambdaRange.getLL(1));
        Assertions.assertNull(lambdaRange.getLL(11));

        lambdaRange.add(300e-9, 700e-9);

        Assertions.assertEquals(0, lambdaRange.getDlambda(1), 1e-12);
        Assertions.assertEquals(40e-9, lambdaRange.getDlambda(11), 1e-12);
        Assertions.assertNull(lambdaRange.getLL(1));

        double[] ll = lambdaRange.getLL(11);

        Assertions.assertNotNull(ll);
        Assertions.assertEquals(11, ll.length);
        Assertions.assertEquals(300e-9, ll[0]);
        Assertions.assertEquals(700e-9, ll[10]);
    }

    @Test
    public void lambdaRangeDopantTest() {
        //TODO
    }
}
