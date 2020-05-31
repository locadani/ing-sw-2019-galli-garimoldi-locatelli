package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.SentinelDecorator;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MinotaurTest {

    Divinity minotaur = null;
    Divinity opponent = null;
    Board board = null;
    Coordinates origin1 = null;
    Coordinates origin2 = null;
    Coordinates destination1 = null;
    Coordinates destination2 = null;
    Coordinates nextInLine = null;

    @Before
    public void setUp() {
        Player player1 = new Player("a", 1);
        Player player2 = new Player("b", 2);
        minotaur = DivinityFactory.create("Minotaur");
        opponent = DivinityFactory.create("Artemis");
        board = new DebugBoard();
        minotaur.setBoard(board);
        opponent.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        SentinelDecorator mediator = new SentinelDecorator(divinityMediator);
        minotaur.setDivinityMediator(mediator);
        opponent.setDivinityMediator(mediator);


        player1.setDivinity(minotaur);
        player2.setDivinity(opponent);

        origin1 = new Coordinates(7);
        origin2 = new Coordinates(12);
        destination1 = new Coordinates(8);
        destination2 = new Coordinates(11);

        minotaur.placeWorker(new Worker(origin1, player1), origin1);
        minotaur.placeWorker(new Worker(origin2, player1), origin2);
        opponent.placeWorker(new Worker(destination1, player2), destination1);
        opponent.placeWorker(new Worker(destination2, player2), destination2);
    }

    @After
    public void tearDown() {
        minotaur = null;
        opponent = null;
    }

    @Test
    public void MinotaurGodPowerTestValidMove() {
        minotaur.selectWorker(origin1);
        minotaur.move(destination1);
        nextInLine = new Coordinates(2 * destination1.getR() - origin1.getR(), 2 * destination1.getC() - origin1.getC());
        assertTrue(board.getSquare(destination1).getTop().equals(minotaur.selectedWorker)
                && ((Worker) board.getSquare(nextInLine).getTop()).getPlayer().getDivinity().getName().equals(opponent.getName()));
    }

    @Test
    public void MinotaurGodPowerNoAvailableSquare() {
        minotaur.selectWorker(origin1);
        assertFalse(minotaur.move(destination2));
    }

    @Test
    public void MinotaurGodPowerOnOwnWorker() {
        minotaur.selectWorker(origin1);
        assertFalse(minotaur.move(origin2));
    }
}
