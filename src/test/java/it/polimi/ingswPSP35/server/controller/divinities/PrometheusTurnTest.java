package it.polimi.ingswPSP35.server.controller.divinities;


import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.Winner;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PrometheusTurnTest {
    private Prometheus god = null;
    private AbstractTurn turn = null;
    private Coordinates worker = null;
    private Coordinates square = null;


    @Before
    public void setUp() {
        god = new PrometheusMock();
        turn = god.getTurn();
    }


    @Test
    public void possibleTurnsTest() {
        List<Action> turn1 = new ArrayList<Action>(List.of(Action.BUILD, Action.MOVE, Action.BUILD, Action.ENDTURN));
        List<Action> turn2 = new ArrayList<Action>(List.of(Action.MOVE, Action.BUILD, Action.ENDTURN));

        List<List<Action>> validTurns = List.of(turn1, turn2);

        assertTrue(TestHelperFunctions.turnsAreValid(new PrometheusMock(), validTurns));
    }

}

class PrometheusMock extends Prometheus {
    @Override
    public boolean move(Coordinates destination) {
        return true;
    }

    @Override
    public boolean build(Coordinates target) {
        return true;
    }

    @Override
    public boolean restrictedMove(Coordinates destination) {
        return true;
    }

    @Override
    public boolean selectWorker(Coordinates w) {
        return true;
    }
}