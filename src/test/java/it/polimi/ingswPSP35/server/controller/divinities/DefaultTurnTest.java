package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.Winner;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultTurnTest {

    Divinity divinity = DivinityFactory.create("Pan");
    AbstractTurn turn = divinity.getTurn();
    Coordinates origin = null;
    Board board;

    RequestedAction moveFrom1to2 = new RequestedAction(1, Action.MOVE, 2);
    RequestedAction noWorkerMove = new RequestedAction(2, Action.MOVE, 2);
    RequestedAction buildOn6 = new RequestedAction(99, Action.BUILD, 6);
    RequestedAction godpower = new RequestedAction(2, Action.GODPOWER, 3);
    RequestedAction endTurn = new RequestedAction(2, Action.ENDTURN, 6);
    RequestedAction cannotBuild = new RequestedAction(2, Action.BUILD, 20);


    @Before
    public void setUp()
    {
        Player player1 = new Player("a", 1);
        board = new Board();
        divinity.setBoard(board);


        player1.setDivinity(divinity);

        divinity.setDivinityMediator(new DivinityMediator());

        origin = new Coordinates(1);
        Square originSquare = board.getSquare(origin);

        board.getSquare(new Coordinates(2)).insert(new Block());

        divinity.placeWorker(new Worker(origin, player1), origin);

    }

    @Test
    public void tryAction() {
        assertFalse(turn.tryAction(noWorkerMove.getWorker(), noWorkerMove.getAction(), noWorkerMove.getSquare()));
        assertTrue(turn.tryAction(moveFrom1to2.getWorker(), moveFrom1to2.getAction(), moveFrom1to2.getSquare()));
        assertTrue(turn.tryAction(buildOn6.getWorker(), buildOn6.getAction(), buildOn6.getSquare()));
        assertTrue(turn.tryAction(endTurn.getWorker(), endTurn.getAction(), endTurn.getSquare()));
    }

    @Test
    public void buildDomeTest()
    {
        Square originSquare = board.getSquare(new Coordinates(6));
        originSquare.insert(new Block());
        originSquare.insert(new Block());
        originSquare.insert(new Block());
        assertTrue(turn.tryAction(moveFrom1to2.getWorker(), moveFrom1to2.getAction(), moveFrom1to2.getSquare()));
        assertFalse(turn.tryAction(cannotBuild.getWorker(), cannotBuild.getAction(), cannotBuild.getSquare()));
        assertTrue(turn.tryAction(buildOn6.getWorker(), buildOn6.getAction(), buildOn6.getSquare()));
        assertTrue(turn.tryAction(endTurn.getWorker(), endTurn.getAction(), endTurn.getSquare()));
    }

    @Test
    public void placeWorkerTest()
    {
        assertFalse(divinity.placeWorker(new Worker(origin, new Player("Name",2)), origin));
    }
}