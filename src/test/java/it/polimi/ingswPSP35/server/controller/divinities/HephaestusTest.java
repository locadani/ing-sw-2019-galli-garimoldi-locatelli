package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.SentinelDecorator;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.TestHelperFunctions;
import it.polimi.ingswPSP35.server.model.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HephaestusTest {

    private Divinity hephaestus = null;
    private Board board = null;
    private AbstractTurn turn;

    private RequestedAction notPossibleAction = new RequestedAction(1, Action.BUILD, 2);
    private RequestedAction moveFrom1to2 = new RequestedAction(1, Action.MOVE, 2);
    private RequestedAction noWorkerMove = new RequestedAction(2, Action.MOVE, 2);
    private RequestedAction buildOn6 = new RequestedAction(99, Action.BUILD, 6);
    private RequestedAction endTurn = new RequestedAction(2, Action.ENDTURN, 6);


    @Before
    public void setUp() {
        Player player = new Player("a", 1);
        hephaestus = DivinityFactory.create("Hephaestus");
        board = new Board();
        hephaestus.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        SentinelDecorator mediator = new SentinelDecorator(divinityMediator);
        hephaestus.setDivinityMediator(mediator);

        player.setDivinity(hephaestus);

        hephaestus.placeWorker(new Worker(new Coordinates(1), player), new Coordinates(1));
    }

    @Test
    public void HephaestusTurn()
    {
        turn = hephaestus.getTurn();
        assertFalse(turn.tryAction(notPossibleAction.getWorker(),notPossibleAction.getAction(),notPossibleAction.getSquare()));
        assertFalse(turn.tryAction(noWorkerMove.getWorker(),noWorkerMove.getAction(),noWorkerMove.getSquare()));
        assertTrue(turn.tryAction(moveFrom1to2.getWorker(),moveFrom1to2.getAction(),moveFrom1to2.getSquare()));
        assertTrue(turn.tryAction(buildOn6.getWorker(),buildOn6.getAction(),buildOn6.getSquare()));
        assertTrue(turn.tryAction(buildOn6.getWorker(),buildOn6.getAction(),buildOn6.getSquare()));
        assertTrue(turn.tryAction(endTurn.getWorker(),endTurn.getAction(),endTurn.getSquare()));
    }
}