package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.Winner;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LimusTest {

    private Player player, opponent;
    private Board board;
    private Winner winner;
    private DivinityMediator divinityMediator;
    private Worker playerWorker, opponentWorker;

    @Before
    public void setUp()
    {
        /*
        Player worker in cell 1
        Opponent worker on height 3 tower in cell 7
         */
        winner = new Winner();
        board = new Board();
        player = new Player("Player", 1);
        playerWorker = new Worker(new Coordinates(1), player);
        player.setDivinity(DivinityFactory.create("Limus"));
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
        opponent.setDivinity(DivinityFactory.create("Hera"));
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
    public void notValidCellForWorkerTest()
    {
        assertFalse(player.getDivinity().placeWorker(playerWorker, playerWorker.getCoordinates()));
    }
    @Test

    public void opponentCannotBuildBlockTest() {
        assertFalse(opponent.getDivinity().build(new Coordinates(6)));
    }

    //create a height 3 tower where to buìld one dome
    @Test
    public void opponentCanBuildDomeTest() {
        board.getSquare(new Coordinates(6)).insert(new Block());
        board.getSquare(new Coordinates(6)).insert(new Block());
        board.getSquare(new Coordinates(6)).insert(new Block());
        assertTrue(opponent.getDivinity().build(new Coordinates(6)));
    }

    //
    @Test
    public void LimusBuildTest()
    {
        assertTrue(divinityMediator.checkBuild(playerWorker, board.getSquare(playerWorker.getCoordinates()), board.getSquare(new Coordinates(2))));
    }
}