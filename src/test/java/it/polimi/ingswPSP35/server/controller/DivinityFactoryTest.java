package it.polimi.ingswPSP35.server.controller;

import static org.junit.Assert.*;

import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.controller.divinities.Minotaur;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DivinityFactoryTest {

    String s = null;

    @Before
    public void setUp() throws Exception {
        s = "Minotaur";
    }

    @After
    public void tearDown() throws Exception {
        s = null;
    }


    @Test
    public void createBasicTest() {
         Divinity d = DivinityFactory.create(s);
         assertTrue(d instanceof Minotaur);
    }
}