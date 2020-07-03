package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrometheusTest {

    private AbstractTurn turn;
    private Board board;
    private Coordinates origin;
    private Divinity prometheus;
    private Player player1 = new Player("a", 1);
    private Worker worker = new Worker(origin, player1);

    @Before
    public void setUp()
    {
        /*
            Player worker on height one tower in cell 1
            Height two tower in cell 2
         */
        prometheus = DivinityFactory.create("Prometheus");
        board = new Board();
        player1.setDivinity(prometheus);
        prometheus.setBoard(board);
        prometheus.setDivinityMediator(new DivinityMediator());
        board.getSquare(new Coordinates(1)).insert(new Block());

        origin = new Coordinates(1);

        board.getSquare(new Coordinates(2)).insert(new Block());
        board.getSquare(new Coordinates(2)).insert(new Block());


        prometheus.placeWorker(worker, origin);
        turn = prometheus.getTurn();
    }

    @Test
    public void cannotMoveUpAfterBuildingTest() {
        turn.tryAction(new Coordinates(1),Action.BUILD, new Coordinates(7));
        assertFalse(turn.tryAction(new Coordinates(1), Action.MOVE, new Coordinates(2)));
    }

    @Test
    public void doubleBuildTest()
    {
        turn.tryAction(new Coordinates(1),Action.BUILD, new Coordinates(7));
        turn.tryAction(new Coordinates(1), Action.MOVE, new Coordinates(7));
        assertTrue(turn.tryAction(new Coordinates(7), Action.BUILD, new Coordinates(6)));
    }

    @Test
    public void doubleBuildBeforeMovingTest()
    {
        turn.tryAction(new Coordinates(1),Action.BUILD, new Coordinates(7));
        assertFalse(turn.tryAction(new Coordinates(1), Action.BUILD, new Coordinates(7)));
    }

    @Test
    public void cannotEndturnWithoutBuildingAfterMovingTest()
    {
        turn.tryAction(new Coordinates(1),Action.BUILD, new Coordinates(7));
        turn.tryAction(new Coordinates(1), Action.MOVE, new Coordinates(7));
        assertFalse(turn.tryAction(new Coordinates(7), Action.ENDTURN, new Coordinates(6)));
    }

    @Test
    public void impossibleMoveTest()
    {
        assertFalse(turn.tryAction(new Coordinates(2), Action.MOVE, new Coordinates(2)));
    }
}