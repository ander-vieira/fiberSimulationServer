package com.fibersim.fiberSimulationServer.core.element;

import com.fibersim.fiberSimulationServer.core.basics.Ray;
import com.fibersim.fiberSimulationServer.core.check.ZIntervalCheck;
import com.fibersim.fiberSimulationServer.core.util.LambdaRange;
import com.fibersim.fiberSimulationServer.core.util.Vector3;
import com.fibersim.fiberSimulationServer.resources.resource.LambdaConstantResource;
import com.fibersim.fiberSimulationServer.resources.resource.MediumResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ElementTest {
    @Mock
    MediumResource mediumResource;

    @Test
    @Tag("UnitTest")
    public void attenuatorElementTest() {
        LambdaRange lambdaRange = new LambdaRange(440e-9, 740e-9);
        Ray ray1 = new Ray(Vector3.O, Vector3.X, 1, 0);

        Mockito.when(mediumResource.getAttenuation()).thenReturn(LambdaConstantResource.builder().value(0).build());

        AttenuatorElement element = new AttenuatorElement(mediumResource, lambdaRange.getLL(11));
        element.setCheck(new ZIntervalCheck(0, 1));

        Assertions.assertEquals(Double.POSITIVE_INFINITY, element.intersectionPoint(ray1), 1e-12);

        Assertions.assertTrue(ray1.alive());
        element.process(ray1, 1);
        Assertions.assertFalse(ray1.alive());

        Ray ray2 = new Ray(new Vector3(0, 0, 1.5), Vector3.X, 1, 0);
        Assertions.assertTrue(ray2.alive());
        element.process(ray2, 1);
        Assertions.assertTrue(ray2.alive());
    }

    @Test
    @Tag("UnitTest")
    public void collectorElementTest() {
        //TODO
    }

    @Test
    @Tag("UnitTest")
    public void dyeDopantElementTest() {
        //TODO
    }

    @Test
    @Tag("UnitTest")
    public void mirrorElementTest() {
        //TODO
    }

    @Test
    @Tag("UnitTest")
    public void refractorElementTest() {
        //TODO
    }
}
