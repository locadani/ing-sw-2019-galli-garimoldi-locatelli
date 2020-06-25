package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {

    Worker myWorker, myWorker2, notMyWorker;
    Player me, notMe;

    @Before
    public void setUp()
    {
        me = new Player("Me", 1);
        notMe = new Player("NotMe", 2);
        myWorker = new Worker(new Coordinates(1), me);
        myWorker2 = new Worker(new Coordinates(5), me);
        notMyWorker = new Worker(new Coordinates(2), notMe);
        me.setWorker(0, myWorker);
        me.setWorker(0, myWorker2);
        me.setDivinity(DivinityFactory.create("Apollo"));
    }


    private boolean playersAreEqual(Player player1, Player player2)
    {
        if(!player1.getUsername().equals(player2.getUsername()))
            return false;
        if(player1.getAge() != player2.getAge())
            return false;
        if(!player1.getDivinity().equals(player2.getDivinity()))
            return false;
        for(Worker worker: player1.getWorkerList())
        {
            if(!isMyWorker(worker.getCoordinates(), player2))
                return false;
        }
        if(player1.getColour() != player2.getColour())
            return false;

        return true;
    }

    private boolean isMyWorker(Coordinates worker, Player player)
    {

        for(Worker w: player.getWorkerList())
        {
            if(w.getCoordinates().equals(worker))
                return true;
        }
        return false;
    }
}