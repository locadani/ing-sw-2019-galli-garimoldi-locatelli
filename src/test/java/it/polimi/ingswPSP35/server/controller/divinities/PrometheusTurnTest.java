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
    Prometheus god = null;
    AbstractTurn turn = null;
    Coordinates worker = null;
    Coordinates square = null;


    @Before
    public void setUp() {
        god = new PrometheusMock();
        turn = god.getTurn();
    }

    @Test
    public void restrictedMoveTest()
    {
        Board board;
        Coordinates origin;
        Divinity prometheus;
        Player player1 = new Player("a", 1);
        prometheus = DivinityFactory.create("Prometheus");
        board = new Board();
        prometheus.setBoard(board);

        player1.setDivinity(prometheus);

        prometheus.setDivinityMediator(new DivinityMediator());

        board.getSquare(new Coordinates(1)).insert(new Block());

        origin = new Coordinates(1);

        board.getSquare(new Coordinates(2)).insert(new Block());
        board.getSquare(new Coordinates(2)).insert(new Block());


        prometheus.placeWorker(new Worker(origin, player1), origin);

        turn = prometheus.getTurn();

        assertFalse(turn.tryAction(new Coordinates(2), Action.MOVE, new Coordinates(2)));
        assertTrue(turn.tryAction(new Coordinates(1),Action.BUILD, new Coordinates(7)));
        assertFalse(turn.tryAction(new Coordinates(1), Action.MOVE, new Coordinates(2)));
        assertTrue(turn.tryAction(new Coordinates(1), Action.MOVE, new Coordinates(6)));
        assertTrue(turn.tryAction(new Coordinates(2), Action.BUILD, new Coordinates(1)));
        assertTrue(turn.tryAction(new Coordinates(2), Action.ENDTURN, new Coordinates(1)));
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