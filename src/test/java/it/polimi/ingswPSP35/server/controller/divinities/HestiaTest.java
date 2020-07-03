package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.Winner;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HestiaTest {

    private Player player;
    private Board board;
    private Winner winner;
    private DivinityMediator divinityMediator;
    private Worker playerWorker;
    private AbstractTurn turn;

    @Before
    public void setUp()
    {
        /*
        Player worker in cell 1
         */
        winner = new Winner();
        board = new Board();
        player = new Player("Player", 1);
        playerWorker = new Worker(new Coordinates(1), player);
        player.setDivinity(DivinityFactory.create("Hestia"));
        player.getDivinity().setWinner(winner);
        player.getDivinity().setBoard(board);
        player.getDivinity().placeWorker(playerWorker, playerWorker.getCoordinates());
        player.getDivinity().selectWorker(playerWorker.getCoordinates());
        player.addWorker(playerWorker);

        divinityMediator = new DivinityMediator();
        divinityMediator = player.getDivinity().decorate(divinityMediator);
        player.getDivinity().setDivinityMediator(divinityMediator);
        turn = player.getDivinity().getTurn();

    }

    @Test
    public void selectCellWithoutWorkerTest()
    {
        assertFalse(turn.tryAction(new Coordinates(10), Action.BUILD,new Coordinates(3)));
    }

    @Test
    public void cannotBuildSecondBlockOnPerimetralSquareTest() {
        turn.tryAction(playerWorker.getCoordinates(), Action.MOVE,new Coordinates(2));
        turn.tryAction(playerWorker.getCoordinates(), Action.BUILD,new Coordinates(3));
        assertFalse(turn.tryAction(playerWorker.getCoordinates(), Action.BUILD,new Coordinates(3)));
    }

    @Test
    public void buildSecondBlockOnNotPerimetralSquareTest() {
        turn.tryAction(playerWorker.getCoordinates(), Action.MOVE,new Coordinates(2));
        turn.tryAction(playerWorker.getCoordinates(), Action.BUILD,new Coordinates(3));
        assertTrue(turn.tryAction(playerWorker.getCoordinates(), Action.BUILD,new Coordinates(7)));
    }

    @Test
    public void endTurnTest()
    {
        turn.tryAction(playerWorker.getCoordinates(), Action.MOVE,new Coordinates(2));
        turn.tryAction(playerWorker.getCoordinates(), Action.BUILD,new Coordinates(3));
        assertTrue(turn.tryAction(playerWorker.getCoordinates(), Action.ENDTURN,new Coordinates(3)));
    }

    @Test
    public void notAvailableActionTest()
    {
        assertFalse(turn.tryAction(playerWorker.getCoordinates(), Action.BUILD, new Coordinates(2)));
    }
}