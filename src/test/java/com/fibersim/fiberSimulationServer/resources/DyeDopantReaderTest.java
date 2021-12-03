package com.fibersim.fiberSimulationServer.resources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DyeDopantReaderTest {
    @Autowired
    DyeDopantReader dyeDopantReader;

    @Test
    void jsonMapReaderTest() {
        DyeDopantResource params = dyeDopantReader.readDopant("Rh6G");

        Assertions.assertNotNull(params);
        Assertions.assertInstanceOf(DyeDopantResource.class, params);
        Assertions.assertEquals("Rh6G", params.getDopant());
    }
}
