package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.SentinelDecorator;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtemisTest {

    private Divinity artemis = null;
    private Board board;
    private AbstractTurn turn;
    private RequestedAction notAllowedBuild = new RequestedAction(1, Action.BUILD, 2);
    private RequestedAction moveFrom1to2 = new RequestedAction(1, Action.MOVE, 2);
    private RequestedAction moveFrom2to1 = new RequestedAction(2, Action.MOVE, 1);
    private RequestedAction moveFrom2to3 = new RequestedAction(2, Action.MOVE, 3);
    private RequestedAction noWorkerMove = new RequestedAction(2, Action.MOVE, 2);
    private RequestedAction buildOn7 = new RequestedAction(99, Action.BUILD, 7);
    private RequestedAction endTurn = new RequestedAction(2, Action.ENDTURN, 6);

    @Before
    public void setUp() {

        /*
        player worker in cell 1
         */
        Player player = new Player("a", 1);
        artemis = DivinityFactory.create("Artemis");
        board = new Board();
        artemis.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        SentinelDecorator mediator = new SentinelDecorator(divinityMediator);
        artemis.setDivinityMediator(mediator);

        player.setDivinity(artemis);

        artemis.placeWorker(new Worker(new Coordinates(1), player), new Coordinates(1));
        turn = artemis.getTurn();

    }

    @Test
    public void getNameTest() {
        String s = artemis.getName();
        assertEquals("Artemis", s);
    }


    @Test
    public void moveTwiceTest()
    {
        turn.tryAction(moveFrom1to2.getWorker(),moveFrom1to2.getAction(),moveFrom1to2.getSquare());
        turn.tryAction(moveFrom2to3.getWorker(),moveFrom2to3.getAction(),moveFrom2to3.getSquare());
        assertTrue(turn.tryAction(buildOn7.getWorker(),buildOn7.getAction(),buildOn7.getSquare()));
    }

    @Test
    public void cannotReturnToStartingCellTest()
    {
        turn.tryAction(moveFrom1to2.getWorker(),moveFrom1to2.getAction(),moveFrom1to2.getSquare());
        assertFalse(turn.tryAction(moveFrom2to1.getWorker(),moveFrom2to1.getAction(),moveFrom2to1.getSquare()));
    }

    @Test
    public void normalTurnTest()
    {
        turn.tryAction(moveFrom1to2.getWorker(),moveFrom1to2.getAction(),moveFrom1to2.getSquare());
        turn.tryAction(buildOn7.getWorker(),buildOn7.getAction(),buildOn7.getSquare());
        assertTrue(turn.tryAction(endTurn.getWorker(),endTurn.getAction(),endTurn.getSquare()));
    }

    @Test
    public void notAllowedActionTest()
    {
        assertFalse(turn.tryAction(notAllowedBuild.getWorker(),notAllowedBuild.getAction(),notAllowedBuild.getSquare()));
    }

    @Test
    public void movingWorkerFromEmptySquareTest()
    {
        assertFalse(turn.tryAction(noWorkerMove.getWorker(),noWorkerMove.getAction(),noWorkerMove.getSquare()));
    }
}