package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.SentinelDecorator;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasTest {


    private Divinity atlas = null;
    private Board board = null;
    private AbstractTurn turn;

    private RequestedAction notPossibleAction = new RequestedAction(1, Action.BUILD, 2);
    private RequestedAction moveFrom1to2 = new RequestedAction(1, Action.MOVE, 2);
    private RequestedAction noWorkerMove = new RequestedAction(2, Action.MOVE, 2);
    private RequestedAction buildOn6 = new RequestedAction(99, Action.BUILD, 6);
    private RequestedAction godpower = new RequestedAction(2, Action.GODPOWER, 3);
    private RequestedAction endTurn = new RequestedAction(2, Action.ENDTURN, 6);
    private RequestedAction cannotBuildDome = new RequestedAction(2, Action.GODPOWER, 20);

    @Before
    public void setUp() {
        Player player = new Player("a", 1);
        atlas = DivinityFactory.create("Atlas");
        board = new Board();
        atlas.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        SentinelDecorator mediator = new SentinelDecorator(divinityMediator);
        atlas.setDivinityMediator(mediator);
        player.setDivinity(atlas);

        atlas.setDivinityMediator(mediator);
        atlas.placeWorker(new Worker(new Coordinates(1), player), new Coordinates(1));
        atlas.selectWorker(new Coordinates(1));
    }

    @Test
    public void normalBuildTest()
    {
        turn = atlas.getTurn();
        assertFalse(turn.tryAction(noWorkerMove.getWorker(),noWorkerMove.getAction(),noWorkerMove.getSquare()));
        assertFalse(turn.tryAction(notPossibleAction.getWorker(),notPossibleAction.getAction(),notPossibleAction.getSquare()));
        assertTrue(turn.tryAction(moveFrom1to2.getWorker(),moveFrom1to2.getAction(),moveFrom1to2.getSquare()));
        assertTrue(turn.tryAction(buildOn6.getWorker(),buildOn6.getAction(),buildOn6.getSquare()));
        assertTrue(turn.tryAction(endTurn.getWorker(),endTurn.getAction(),endTurn.getSquare()));
    }

    @Test
    public void godpowerBuildTest()
    {
        turn = atlas.getTurn();
        assertTrue(turn.tryAction(moveFrom1to2.getWorker(),moveFrom1to2.getAction(),moveFrom1to2.getSquare()));
        assertTrue(turn.tryAction(godpower.getWorker(),godpower.getAction(),godpower.getSquare()));
        assertTrue(turn.tryAction(endTurn.getWorker(),endTurn.getAction(),endTurn.getSquare()));
    }

    @Test
    public void cannotBuildDomeTest()
    {
        turn = atlas.getTurn();
        assertTrue(turn.tryAction(moveFrom1to2.getWorker(),moveFrom1to2.getAction(),moveFrom1to2.getSquare()));
        assertFalse(turn.tryAction(cannotBuildDome.getWorker(),cannotBuildDome.getAction(),cannotBuildDome.getSquare()));
    }
}