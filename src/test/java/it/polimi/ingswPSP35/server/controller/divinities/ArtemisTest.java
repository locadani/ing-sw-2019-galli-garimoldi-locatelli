package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.controller.divinities.Artemis;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtemisTest {

    Divinity a = null;

    @Before
    public void setUp() throws Exception {
        a = new Artemis();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNameTest() {
        String s = a.getName();
        assertEquals("Artemis", s);
    }
}