package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.Winner;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PanVictoryTest {
    private Divinity pan = null;
    private Winner winner = null;

    private Board board = null;
    private Coordinates origin = null;

    @Before
    public void setUp() {
        Player player1 = new Player("a", 1);
        pan = DivinityFactory.create("Pan");
        board = new Board();
        pan.setBoard(board);

        winner = new Winner();
        pan.setWinner(winner);

        player1.setDivinity(pan);

        pan.setDivinityMediator(new DivinityMediator());

        origin = new Coordinates(7);
        Square originSquare = board.getSquare(origin);
        originSquare.insert(new Block());
        originSquare.insert(new Block());
        originSquare.insert(new Block());

        board.getSquare(new Coordinates(12)).insert(new Block());

        pan.placeWorker(new Worker(origin, player1), origin);
    }

    @Test
    public void PanWinsWithGodPowerFromLvl3ToLvl1() {
        pan.selectWorker(origin);
        pan.move(new Coordinates(12));
        assertEquals(winner.getWinner().getName(), pan.getName());
    }

    @Test
    public void PanWinsWithGodPowerFromLvl3ToLvl0() {
        pan.selectWorker(origin);
        pan.move(new Coordinates(11));
        assertEquals(winner.getWinner().getName(), pan.getName());
    }

    @Test
    public void PanDoesntWinWithGodPowerFromLvl3ToLvl2() {
        Coordinates coordinates = new Coordinates(13);
        board.getSquare(coordinates).insert(new Block());
        board.getSquare(coordinates).insert(new Block());
        pan.selectWorker(origin);
        pan.move(coordinates);
        assertNull(winner.getWinner());
    }


    @Test
    public void PanWinsWithGodPowerFromLvl2ToLvl0() {
        Coordinates coordinates = new Coordinates(13);
        board.getSquare(coordinates).insert(new Block());
        board.getSquare(coordinates).insert(new Block());
        pan.selectWorker(origin);
        pan.move(coordinates);
        pan.move(new Coordinates(14));
        assertEquals(winner.getWinner().getName(), pan.getName());
    }

    @Test
    public void PanWinsNormally() {
        Coordinates coordinates = new Coordinates(13);
        board.getSquare(coordinates).insert(new Block());
        board.getSquare(coordinates).insert(new Block());
        pan.selectWorker(origin);
        pan.move(coordinates);
        pan.move(origin);
        assertEquals(winner.getWinner().getName(), pan.getName());
    }

}
