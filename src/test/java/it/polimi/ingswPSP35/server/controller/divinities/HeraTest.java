package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DefeatChecker;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.Winner;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeraTest {

    Player player, opponent;
    Board board;
    Winner winner;
    DivinityMediator divinityMediator;
    Worker playerWorker, opponentWorker;

    @Before
    public void setUp()
    {
        winner = new Winner();
        board = new Board();
        player = new Player("Player", 1);
        playerWorker = new Worker(new Coordinates(1), player);
        player.setDivinity(DivinityFactory.create("Hera"));
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

    @Test
    public void PanCanWinAndHeraCannotForbidItTest()
    {
        assertTrue(divinityMediator.checkWin(opponentWorker, board.getSquare(new Coordinates(8)), board.getSquare(opponentWorker.getCoordinates())));
    }

    @Test
    public void HeraBuildTest()
    {
        assertTrue(divinityMediator.checkWin(playerWorker, board.getSquare(new Coordinates(2)), board.getSquare(playerWorker.getCoordinates())));
    }
}