package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.SentinelDecorator;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DemeterTest {

    Divinity demeter = null;
    Board board = null;

    RequestedAction moveFrom1to2 = new RequestedAction(1, Action.MOVE, 2);
    RequestedAction buildOn7 = new RequestedAction(99, Action.BUILD, 7);
    RequestedAction buildOn6 = new RequestedAction(99, Action.BUILD, 6);
    RequestedAction buildOn6Alt = new RequestedAction(99, Action.BUILD, 6);

    @Before
    public void setUp() {
        Player player = new Player("a", 1);
        demeter = DivinityFactory.create("Demeter");
        board = new Board();
        demeter.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        SentinelDecorator mediator = new SentinelDecorator(divinityMediator);
        demeter.setDivinityMediator(mediator);

        player.setDivinity(demeter);

        demeter.placeWorker(new Worker(new Coordinates(1), player), new Coordinates(1));
    }

    @Test
    public void DemeterTurnTest() {
        AbstractTurn turn = demeter.getTurn();
        assertTrue(turn.tryAction(moveFrom1to2.getWorker(),moveFrom1to2.getAction(),moveFrom1to2.getSquare()));
        assertTrue(turn.tryAction(buildOn6.getWorker(),buildOn6.getAction(),buildOn6.getSquare()));
        assertFalse(turn.tryAction(buildOn6Alt.getWorker(),buildOn6Alt.getAction(),buildOn6Alt.getSquare()));
        assertTrue(turn.tryAction(buildOn7.getWorker(),buildOn7.getAction(),buildOn7.getSquare()));
    }

}
