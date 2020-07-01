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

    private Divinity minotaur = null;
    private Divinity opponentDivinity = null;
    private Board board = null;
    private Coordinates origin1 = null;
    private Coordinates origin2 = null;
    private Coordinates destination1 = null;
    private Coordinates destination2 = null;
    private Coordinates nextInLine = null;

    @Before
    public void setUp() {
        
        /*
            Player workers are in cell 7 and 12
            Opponent workers are in cell 8 and 11
         */
        Player player = new Player("a", 1);
        Player opponent = new Player("b", 2);
        minotaur = DivinityFactory.create("Minotaur");
        opponentDivinity = DivinityFactory.create("Artemis");
        board = new Board();
        minotaur.setBoard(board);
        opponentDivinity.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        SentinelDecorator mediator = new SentinelDecorator(divinityMediator);
        minotaur.setDivinityMediator(mediator);
        opponentDivinity.setDivinityMediator(mediator);


        player.setDivinity(minotaur);
        opponent.setDivinity(opponentDivinity);

        origin1 = new Coordinates(7);
        origin2 = new Coordinates(12);
        destination1 = new Coordinates(8);
        destination2 = new Coordinates(11);

        minotaur.placeWorker(new Worker(origin1, player), origin1);
        minotaur.placeWorker(new Worker(origin2, player), origin2);
        opponentDivinity.placeWorker(new Worker(destination1, opponent), destination1);
        opponentDivinity.placeWorker(new Worker(destination2, opponent), destination2);
    }

    @After
    public void tearDown() {
        minotaur = null;
        opponentDivinity = null;
    }

    @Test
    public void MinotaurGodPowerTestValidMoveTest() {
        minotaur.selectWorker(origin1);
        minotaur.move(destination1);
        nextInLine = new Coordinates(2 * destination1.getR() - origin1.getR(), 2 * destination1.getC() - origin1.getC());
        assertTrue(board.getSquare(destination1).getTop().equals(minotaur.selectedWorker)
                && ((Worker) board.getSquare(nextInLine).getTop()).getPlayer().getDivinity().getName().equals(opponentDivinity.getName()));
    }

    @Test
    public void MinotaurGodPowerNoAvailableSquareTest() {
        minotaur.selectWorker(origin1);
        assertFalse(minotaur.move(destination2));
    }

    @Test
    public void MinotaurGodPowerOnOwnWorkerTest() {
        minotaur.selectWorker(origin1);
        assertFalse(minotaur.move(origin2));
    }

    @Test
    public void MinotaurCannotMoveTest()
    {
        minotaur.selectWorker(origin1);
        assertFalse(minotaur.move(new Coordinates(24)));
    }

    @Test
    public void UserPerformsNormalMoveTest()
    {
        minotaur.selectWorker(origin1);
        assertTrue(minotaur.move(new Coordinates(6)));
    }
}
