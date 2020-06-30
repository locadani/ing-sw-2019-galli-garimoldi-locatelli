package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.model.Block;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrometheusTest {

    private AbstractTurn turn;
    private Board board;
    private Coordinates origin;
    private Divinity prometheus;
    Player player1 = new Player("a", 1);

    @Before
    public void setUp()
    {
        prometheus = DivinityFactory.create("Prometheus");
        board = new Board();
        player1.setDivinity(prometheus);
        prometheus.setBoard(board);
        prometheus.setDivinityMediator(new DivinityMediator());
        turn = prometheus.getTurn();
    }

    @Test
    public void restrictedMoveTest() {
        board.getSquare(new Coordinates(1)).insert(new Block());

        origin = new Coordinates(1);

        board.getSquare(new Coordinates(2)).insert(new Block());
        board.getSquare(new Coordinates(2)).insert(new Block());


        prometheus.placeWorker(new Worker(origin, player1), origin);


        assertTrue(turn.tryAction(new Coordinates(1),Action.BUILD, new Coordinates(7)));
        assertFalse(turn.tryAction(new Coordinates(1), Action.MOVE, new Coordinates(2)));
        assertTrue(turn.tryAction(new Coordinates(1), Action.MOVE, new Coordinates(6)));
        assertTrue(turn.tryAction(new Coordinates(2), Action.BUILD, new Coordinates(1)));
        assertTrue(turn.tryAction(new Coordinates(2), Action.ENDTURN, new Coordinates(1)));
    }

    @Test
    public void impossibleMoveTest()
    {
        assertFalse(turn.tryAction(new Coordinates(2), Action.MOVE, new Coordinates(2)));
    }
}