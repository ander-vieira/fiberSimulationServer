package com.fibersim.fiberSimulationServer.core.phase;

import com.fibersim.fiberSimulationServer.core.util.Vector3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
public class PhaseTest {
    @Test
    @Tag("UnitTest")
    public void phaseTest() {
        Phase planePhase = new PlanePhase(Vector3.Z, Vector3.O);
        Phase cylinderPhase = new CylinderPhase(1, Vector3.O, Vector3.Z);

        Vector3 vec1 = new Vector3(0.8, 0.6, 0.3);
        Vector3 vec2 = new Vector3(0.6, 0.4, 0.8);
        Vector3 vec3 = new Vector3(1.6, 0.6, 0.8);
        Vector3 vec4 = new Vector3(-1, 0, 0);

        Assertions.assertEquals(0, planePhase.getNormalVector(Vector3.X).minus(Vector3.Z).norm(), 1e-12);
        Assertions.assertEquals(0, cylinderPhase.getNormalVector(vec1).minus(vec1.projectTangent(Vector3.Z)).norm(), 1e-12);

        Assertions.assertEquals(0.8, planePhase.intersectionPoint(vec3, new Vector3(0, 0, -1)), 1e-12);
        Assertions.assertEquals(Double.POSITIVE_INFINITY, planePhase.intersectionPoint(vec2, Vector3.Z), 1e-12);
        Assertions.assertEquals(0.8, cylinderPhase.intersectionPoint(vec3, vec4), 1e-12);
        Assertions.assertEquals(1, cylinderPhase.intersectionPoint(Vector3.O, vec4), 1e-12);
        Assertions.assertEquals(Double.POSITIVE_INFINITY, cylinderPhase.intersectionPoint(vec3, Vector3.X));
        Assertions.assertEquals(Double.POSITIVE_INFINITY, cylinderPhase.intersectionPoint(vec3, Vector3.Y));
    }
}
