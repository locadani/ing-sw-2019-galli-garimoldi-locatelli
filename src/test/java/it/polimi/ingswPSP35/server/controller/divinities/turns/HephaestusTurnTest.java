package it.polimi.ingswPSP35.server.controller.divinities.turns;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.controller.divinities.Hephaestus;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.TestHelperFunctions;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HephaestusTurnTest {

    private Hephaestus god = null;
    private AbstractTurn turn = null;
    private Coordinates worker = null;
    private Coordinates square = null;


    @Before
    public void setUp() {
        god = new HephaestusMock();
        turn = god.getTurn();
        god.setBoard(new Board());
    }

    @Test
    public void possibleTurnsTest() {
        List<Action> turn1 = new ArrayList<Action>(List.of(Action.MOVE, Action.BUILD, Action.BUILD, Action.ENDTURN));
        List<Action> turn2 = new ArrayList<Action>(List.of(Action.MOVE, Action.BUILD, Action.ENDTURN));

        List<List<Action>> validTurns = List.of(turn1, turn2);

        assertTrue(TestHelperFunctions.turnsAreValid(god, validTurns));
    }
}

class HephaestusMock extends Hephaestus
{
    @Override
    public boolean selectWorker(Coordinates workerCoordinates) {
        return true;
    }

    @Override
    public boolean move(Coordinates destinationCoordinates) {
        return true;
    }

    @Override
    public boolean build(Coordinates targetCoordinates) {
        return true;
    }
}