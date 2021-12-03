package com.fibersim.fiberSimulationServer.core.check;

import com.fibersim.fiberSimulationServer.core.util.Vector3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
public class CheckTest {
    @Test
    @Tag("UnitTest")
    public void checkTest() {
        Check cylinderCheck = new CylinderCheck(1);
        Check cylinderCheck2 = new CylinderCheck(1, Vector3.X);
        Check zIntervalCheck = new ZIntervalCheck(0, 1);

        Assertions.assertTrue(Check.alwaysTrue.check(Vector3.O, Vector3.X));
        Assertions.assertTrue(Check.alwaysTrue.check(Vector3.Z, Vector3.Y));
        Assertions.assertTrue(cylinderCheck.check(Vector3.O, Vector3.X));
        Assertions.assertFalse(cylinderCheck.check(new Vector3(1.0, 1.0, 1.0), Vector3.X));
        Assertions.assertFalse(cylinderCheck2.check(new Vector3(0, 0.5, 0), Vector3.X));
        Assertions.assertTrue(zIntervalCheck.check(new Vector3(1, 2, 0.5), Vector3.Z));
        Assertions.assertFalse(zIntervalCheck.check(new Vector3(3, 0, 1.3), Vector3.Y));
    }
}
