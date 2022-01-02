package com.fibersim.fiberSimulationServer.core.basics;

import com.fibersim.fiberSimulationServer.core.util.Vector3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RayTest {
    @Test
    @Tag("UnitTest")
    public void rayTest() {
        Ray ray = new Ray(Vector3.O, Vector3.X, 0, 0);

        Assertions.assertEquals(0, ray.pos.norm(), 1e-12);
        ray.move(1);
        Assertions.assertEquals(0, ray.pos.minus(Vector3.X).norm(), 1e-12);

        Assertions.assertTrue(ray.alive());
        ray.kill();
        Assertions.assertFalse(ray.alive());
    }
}
