package com.fibersim.fiberSimulationServer.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector3Test {
    @Test
    void vector3Test() {
        Vector3 vec1;

        Assertions.assertEquals(0, Vector3.O.norm(), 1e-12);
        Assertions.assertEquals(0, Vector3.O.normalize().norm(), 1e-12);
        Assertions.assertEquals(1, Vector3.X.norm(), 1e-12);
        Assertions.assertEquals(1, Vector3.Y.norm(), 1e-12);
        Assertions.assertEquals(1, Vector3.Z.norm(), 1e-12);
        Assertions.assertEquals(2, Vector3.Z.plus(Vector3.Z).norm(), 1e-12);

        Assertions.assertEquals(0, Vector3.X.dot(Vector3.Y), 1e-12);

        vec1 = new Vector3(3, 4, 5);
        Assertions.assertEquals(1, vec1.normalize().norm(), 1e-12);
        Assertions.assertEquals(vec1.norm()*3, vec1.scale(3).norm(), 1e-12);
        Assertions.assertEquals(0, vec1.minus(vec1).norm(), 1e-12);
        Assertions.assertEquals("(3.0,4.0,5.0)", vec1.toString());
        Assertions.assertEquals(5, vec1.projectNormal(Vector3.Z).norm(), 1e-12);
        Assertions.assertEquals(5, vec1.projectTangent(Vector3.X).projectTangent(Vector3.Y).norm(), 1e-12);
    }
}
