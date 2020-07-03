package it.polimi.ingswPSP35.server.controller.divinities.turns;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.controller.divinities.Atlas;
import it.polimi.ingswPSP35.server.model.TestHelperFunctions;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AtlasTurnTest {

    private Atlas god = null;

    @Before
    public void setUp() {
        god = new AtlasMock();

    }

    @Test
    public void possibleTurnsTest() {
        ArrayList<Action> turn1 = new ArrayList<Action>(List.of(Action.MOVE, Action.BUILD, Action.ENDTURN));
        ArrayList<Action> turn2 = new ArrayList<Action>(List.of(Action.MOVE, Action.GODPOWER, Action.ENDTURN));

        List<List<Action>> validTurns = List.of(turn1, turn2);

        assertTrue(TestHelperFunctions.turnsAreValid(god, validTurns));
    }
}

class AtlasMock extends Atlas {
    @Override
    public boolean move(Coordinates destination) {
        return true;
    }

    @Override
    public boolean build(Coordinates target) {
        return true;
    }

    @Override
    public boolean selectWorker(Coordinates w){
        return true;
    }

    @Override
    public boolean buildDome(Coordinates targetCoordinates) {
        return true;
    }
}