package com.fibersim.fiberSimulationServer.core.element;

import com.fibersim.fiberSimulationServer.core.basics.Ray;
import com.fibersim.fiberSimulationServer.core.check.ZIntervalCheck;
import com.fibersim.fiberSimulationServer.core.phase.Phase;
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
    MediumResource mediumResource1;
    @Mock
    MediumResource mediumResource2;
    @Mock
    Phase phase;

    @Test
    @Tag("UnitTest")
    public void attenuatorElementTest() {
        LambdaRange lambdaRange = new LambdaRange(440e-9, 740e-9);

        Mockito.when(mediumResource1.getAttenuation()).thenReturn(LambdaConstantResource.builder().value(0).build());

        AttenuatorElement element = new AttenuatorElement(mediumResource1, lambdaRange.getLL(11));
        element.setCheck(new ZIntervalCheck(0, 1));

        Ray ray1 = new Ray(Vector3.O, Vector3.X, 1, 0);

        Assertions.assertEquals(Double.POSITIVE_INFINITY, element.intersectionPoint(ray1), 1e-12);

        Assertions.assertTrue(ray1.alive());
        element.process(ray1, 1);
        Assertions.assertFalse(ray1.alive());

        Ray ray2 = new Ray(new Vector3(0, 0, 1.5), Vector3.X, 1, 0);

        double ds = element.intersectionPoint(ray2);
        Assertions.assertTrue(ds >= 0);

        Assertions.assertTrue(ray2.alive());
        element.process(ray2, ds);
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
        MirrorElement element = new MirrorElement(phase);
        element.setCheck(new ZIntervalCheck(0, 1));

        Mockito.when(phase.intersectionPoint(Mockito.any(Vector3.class), Mockito.any(Vector3.class))).thenReturn((double)1);
        Mockito.when(phase.getNormalVector(Mockito.any(Vector3.class))).thenReturn(Vector3.X);

        Ray ray1 = new Ray(Vector3.O, Vector3.X, 1, 0);

        double ds = element.intersectionPoint(ray1);
        Assertions.assertEquals(1, element.intersectionPoint(ray1), 1e-12);
        element.process(ray1, ds);
        Assertions.assertEquals(0, ray1.vel.plus(Vector3.X).norm(), 1e-12);

        Ray ray2 = new Ray(new Vector3(0, 0, 1.5), Vector3.X, 1, 0);

        ds = element.intersectionPoint(ray2);
        Assertions.assertEquals(1, element.intersectionPoint(ray2), 1e-12);
        element.process(ray2, ds);
        Assertions.assertEquals(0, ray2.vel.minus(Vector3.X).norm(), 1e-12);
    }

    @Test
    @Tag("UnitTest")
    public void refractorElementTest() {
        LambdaRange lambdaRange = new LambdaRange(440e-9, 740e-9);

        Mockito.when(mediumResource1.getRefractionIndex()).thenReturn(LambdaConstantResource.builder().value(1.3).build());
        Mockito.when(mediumResource2.getRefractionIndex()).thenReturn(LambdaConstantResource.builder().value(1.3).build());
        Mockito.when(phase.intersectionPoint(Mockito.any(Vector3.class), Mockito.any(Vector3.class))).thenReturn((double)1);
        Mockito.when(phase.getNormalVector(Mockito.any(Vector3.class))).thenReturn(Vector3.X);

        RefractorElement element = new RefractorElement(phase, mediumResource1, mediumResource2, lambdaRange.getLL(11));
        element.setCheck(new ZIntervalCheck(0, 1));

        Ray ray1 = new Ray(Vector3.O, Vector3.X, 1, 0);

        double ds = element.intersectionPoint(ray1);
        Assertions.assertEquals(1, ds, 1e-12);
        element.process(ray1, ds);
        Assertions.assertEquals(0, ray1.vel.minus(Vector3.X).norm(), 1e-12);

        Ray ray2 = new Ray(Vector3.O, Vector3.X.scale(-1), 1, 0);

        element.process(ray2, ds);
        Assertions.assertEquals(0, ray2.vel.plus(Vector3.X).norm(), 1e-12);
    }
}
