package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.SentinelDecorator;
import it.polimi.ingswPSP35.server.controller.divinities.Artemis;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

//TODO dove fa test artemis
public class ArtemisTest {

    Divinity artemis = null;
    Board board;
    AbstractTurn turn;
    RequestedAction notAllowedBuild = new RequestedAction(1, Action.BUILD, 2);
    RequestedAction moveFrom1to2 = new RequestedAction(1, Action.MOVE, 2);
    RequestedAction moveFrom2to1 = new RequestedAction(2, Action.MOVE, 1);
    RequestedAction moveFrom2to3 = new RequestedAction(2, Action.MOVE, 3);
    RequestedAction noWorkerMove = new RequestedAction(2, Action.MOVE, 2);
    RequestedAction buildOn7 = new RequestedAction(99, Action.BUILD, 7);
    RequestedAction endTurn = new RequestedAction(2, Action.ENDTURN, 6);

    @Before
    public void setUp() throws Exception {
        Player player = new Player("a", 1);
        artemis = DivinityFactory.create("Artemis");
        turn = artemis.getTurn();
        board = new Board();
        artemis.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        SentinelDecorator mediator = new SentinelDecorator(divinityMediator);
        artemis.setDivinityMediator(mediator);

        player.setDivinity(artemis);

        artemis.placeWorker(new Worker(new Coordinates(1), player), new Coordinates(1));

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNameTest() {
        String s = artemis.getName();
        assertEquals("Artemis", s);
    }

    @Test
    public void completeTurnTest()
    {
        assertTrue(turn.tryAction(moveFrom1to2.getWorker(),moveFrom1to2.getAction(),moveFrom1to2.getSquare()));
       // assertFalse(turn.tryAction(moveFrom2to1.getWorker(),moveFrom2to1.getAction(),moveFrom2to1.getSquare()));
        assertTrue(turn.tryAction(moveFrom2to3.getWorker(),moveFrom2to3.getAction(),moveFrom2to3.getSquare()));
        assertTrue(turn.tryAction(buildOn7.getWorker(),buildOn7.getAction(),buildOn7.getSquare()));
        assertTrue(turn.tryAction(endTurn.getWorker(),endTurn.getAction(),endTurn.getSquare()));
    }

    @Test
    public void cannotBuildTest()
    {
        assertFalse(turn.tryAction(notAllowedBuild.getWorker(),notAllowedBuild.getAction(),notAllowedBuild.getSquare()));
    }

    @Test
    public void movingWorkerFromEmptySquareTest()
    {
        assertFalse(turn.tryAction(noWorkerMove.getWorker(),noWorkerMove.getAction(),noWorkerMove.getSquare()));
    }
}