package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import org.junit.Before;

public class PlayerTest {

    Worker myWorker, myWorker2;
    Player me, notMe;

    @Before
    public void setUp()
    {
        me = new Player("Me", 1);
        notMe = new Player("NotMe", 2);
        myWorker = new Worker(new Coordinates(1), me);
        myWorker2 = new Worker(new Coordinates(5), me);
        me.addWorker(myWorker);
        me.addWorker(myWorker2);
        me.setDivinity(DivinityFactory.create("Apollo"));
    }
}