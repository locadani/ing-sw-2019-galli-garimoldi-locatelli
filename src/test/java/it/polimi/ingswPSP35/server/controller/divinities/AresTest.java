package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.Winner;
import it.polimi.ingswPSP35.server.model.Block;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AresTest {

    Player player, opponent;
    Board board;
    Winner winner;
    DivinityMediator divinityMediator;
    Worker playerWorker, playerSecondWorker, opponentWorker;
    AbstractTurn turn;

    @Before
    public void setUp()
    {
        winner = new Winner();
        board = new Board();
        player = new Player("Player", 1);
        playerWorker = new Worker(new Coordinates(1), player);
        playerSecondWorker = new Worker(new Coordinates(11), player);
        player.setDivinity(DivinityFactory.create("Ares"));
        player.getDivinity().setWinner(winner);
        player.getDivinity().setBoard(board);
        player.getDivinity().placeWorker(playerWorker, playerWorker.getCoordinates());
        player.getDivinity().selectWorker(playerWorker.getCoordinates());
        player.addWorker(playerWorker);
        player.getDivinity().placeWorker(playerSecondWorker, playerSecondWorker.getCoordinates());
        player.addWorker(playerSecondWorker);

        board.getSquare(new Coordinates(6)).insert(new Block());
        board.getSquare(new Coordinates(6)).insert(new Block());
        board.getSquare(new Coordinates(6)).insert(new Block());

        board.getSquare(new Coordinates(7)).insert(new Block());
        opponent = new Player("Opponent", 2);
        opponentWorker = new Worker(new Coordinates(7), opponent);
        opponent.setDivinity(DivinityFactory.create("Pan"));
        opponent.getDivinity().setWinner(winner);
        opponent.getDivinity().setBoard(board);
        opponent.getDivinity().placeWorker(opponentWorker, opponentWorker.getCoordinates());
        opponent.getDivinity().selectWorker(opponentWorker.getCoordinates());
        opponent.addWorker(opponentWorker);

        divinityMediator = new DivinityMediator();
        divinityMediator = player.getDivinity().decorate(divinityMediator);
        divinityMediator = opponent.getDivinity().decorate(divinityMediator);
        player.getDivinity().setDivinityMediator(divinityMediator);
        opponent.getDivinity().setDivinityMediator(divinityMediator);
        turn = player.getDivinity().getTurn();
    }

    @Test
    public void invalidPlaceWorkerTest()
    {
        assertFalse(player.getDivinity().placeWorker(playerWorker,playerWorker.getCoordinates()));
    }
    @Test
    public void selectCellWithoutWorkerTest()
    {
        assertFalse(turn.tryAction(new Coordinates(10), Action.BUILD,new Coordinates(3)));
    }

    @Test
    public void aresRemovesBlockTest()
    {
        turn.tryAction(playerWorker.getCoordinates(), Action.MOVE, new Coordinates(2));
        turn.tryAction(playerWorker.getCoordinates(), Action.BUILD, new Coordinates(1));
        assertTrue(turn.tryAction(playerSecondWorker.getCoordinates(), Action.GODPOWER, new Coordinates(6)));
    }

    @Test
    public void aresCannotRemoveDomeTest()
    {
        turn.tryAction(playerWorker.getCoordinates(), Action.MOVE, new Coordinates(2));
        turn.tryAction(playerWorker.getCoordinates(), Action.BUILD, new Coordinates(6));
        assertFalse(turn.tryAction(playerSecondWorker.getCoordinates(), Action.GODPOWER, new Coordinates(6)));
    }

    @Test
    public void notAvailableActionTest()
    {
        assertFalse(turn.tryAction(playerWorker.getCoordinates(), Action.BUILD, new Coordinates(2)));
    }

    @Test
    public void endTurnTest()
    {
        turn.tryAction(playerWorker.getCoordinates(), Action.MOVE,new Coordinates(2));
        turn.tryAction(playerWorker.getCoordinates(), Action.BUILD,new Coordinates(3));
        assertTrue(turn.tryAction(playerWorker.getCoordinates(), Action.ENDTURN, new Coordinates(3)));
    }
}