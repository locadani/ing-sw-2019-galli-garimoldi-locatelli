package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.TestHelperFunctions;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ArtemisTurnTest {
    Artemis god = null;
    AbstractTurn turn = null;
    Coordinates worker = null;
    Coordinates square = null;


    @Before
    public void setUp() {
        god = new ArtemisMock();
        turn = god.getTurn();
    }

    @Test
    public void possibleTurnsTest() {
        List<Action> turn1 = new ArrayList<Action>(List.of(Action.MOVE, Action.MOVE, Action.BUILD, Action.ENDTURN));
        List<Action> turn2 = new ArrayList<Action>(List.of(Action.MOVE, Action.BUILD, Action.ENDTURN));

        List<List<Action>> validTurns = List.of(turn1, turn2);

        assertTrue(TestHelperFunctions.turnsAreValid(new ArtemisMock(), validTurns));
    }

}

class ArtemisMock extends Artemis {
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

}