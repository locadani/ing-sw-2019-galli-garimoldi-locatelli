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

public class HeraTest {

    private Player player, opponent;
    private Board board;
    private Winner winner;
    private DivinityMediator divinityMediator;
    private Worker playerWorker, opponentWorker;
    private Divinity hera = DivinityFactory.create("Hera");

    @Before
    public void setUp()
    {
        /*
        Player worker in cell 1
        Opponent worker on height 3 tower in cell 7
        Tested with Pan who could win by moving down by two or more levels
         */
        winner = new Winner();
        board = new Board();
        player = new Player("Player", 1);
        playerWorker = new Worker(new Coordinates(1), player);
        player.setDivinity(hera);
        player.getDivinity().setWinner(winner);
        player.getDivinity().setBoard(board);
        player.getDivinity().placeWorker(playerWorker, playerWorker.getCoordinates());
        player.getDivinity().selectWorker(playerWorker.getCoordinates());
        player.addWorker(playerWorker);

        board.getSquare(new Coordinates(7)).insert(new Block());
        board.getSquare(new Coordinates(7)).insert(new Block());
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

    }

    @Test
    public void PanCouldWinButHeraDoesntAllowItTest()
    {
        assertFalse(divinityMediator.checkWin(opponentWorker, board.getSquare(new Coordinates(6)), board.getSquare(opponentWorker.getCoordinates())));
    }

    //pan worker moves in not perimetral cell
    @Test
    public void PanCanWinAndHeraCannotForbidItTest()
    {
        assertTrue(divinityMediator.checkWin(opponentWorker, board.getSquare(new Coordinates(8)), board.getSquare(opponentWorker.getCoordinates())));
    }

    @Test
    public void HeraWinTest()
    {
        /*
            Playerworker 2 on height two tower in cell 2
            Height three tower in cell 3
         */
        Worker playerWorker2 = new Worker(new Coordinates(2), player);
        player.addWorker(playerWorker2);
        board.getSquare(new Coordinates(2)).insert(new Block());
        board.getSquare(new Coordinates(2)).insert(new Block());
        board.getSquare(new Coordinates(3)).insert(new Block());
        board.getSquare(new Coordinates(3)).insert(new Block());
        board.getSquare(new Coordinates(3)).insert(new Block());
        hera.selectWorker(playerWorker2.getCoordinates());
        player.getDivinity().placeWorker(playerWorker2, playerWorker2.getCoordinates());
        hera.getTurn().tryAction(playerWorker2.getCoordinates(), Action.MOVE, new Coordinates(3));

        assertTrue(divinityMediator.checkWin(playerWorker, board.getSquare(new Coordinates(2)), board.getSquare(playerWorker.getCoordinates())));
        assertEquals(winner.getWinner().getName(), player.getDivinity().getName());
    }
}