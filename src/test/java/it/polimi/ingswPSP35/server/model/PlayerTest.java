package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.Coordinates;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Worker myWorker, notMyWorker;
    Player me, notMe;

    @Before
    public void setUp()
    {
        me = new Player("Me", 1);
        notMe = new Player("NotMe", 2);
        myWorker = new Worker(new Coordinates(1), me);
        notMyWorker = new Worker(new Coordinates(2), notMe);
        me.setWorker(0, myWorker);
    }
    @Test
    public void isMyWorker() {
        assertTrue(me.isMyWorker(myWorker.getCoordinates()));
        assertFalse(me.isMyWorker(notMyWorker.getCoordinates()));
    }
}