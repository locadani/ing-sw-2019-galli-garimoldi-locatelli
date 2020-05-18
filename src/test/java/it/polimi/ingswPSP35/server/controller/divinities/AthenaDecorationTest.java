package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.SentinelDecorator;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AthenaDecorationTest {

    Divinity athena = null;
    Divinity opponent = null;

    Board board = null;
    Coordinates origin = null;
    Coordinates originOpponent = null;
    Coordinates worker2 = null;

    @Before
    public void setUp() {
        Player player1 = new Player("a", 1);
        Player player2 = new Player("b", 2);
        athena = DivinityFactory.create("Athena");
        opponent = DivinityFactory.create("Demeter");
        board = new DebugBoard();
        athena.setBoard(board);
        opponent.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        divinityMediator = athena.decorate(divinityMediator);
        SentinelDecorator mediator = new SentinelDecorator(divinityMediator);
        athena.setDivinityMediator(mediator);
        opponent.setDivinityMediator(mediator);


        player1.setDivinity(athena);
        player2.setDivinity(opponent);

        origin = new Coordinates(7);
        originOpponent = new Coordinates(8);
        worker2 = new Coordinates(17);

        athena.placeWorker(new Worker(origin, player1), origin);
        athena.placeWorker(new Worker(worker2, player1), worker2);
        opponent.placeWorker(new Worker(originOpponent, player2), originOpponent);
        board.getSquare(new Coordinates(12)).insert(new Block());
        board.getSquare(new Coordinates(13)).insert(new Block());
        board.getSquare(new Coordinates(6)).insert(new Block());
    }

    @Test
    public void AthenaGodpowerTestOpponentCantMoveUp() {
        athena.selectWorker(origin);
        athena.move(new Coordinates(12));
        opponent.selectWorker(originOpponent);
        assertFalse(opponent.move(new Coordinates(13)));
    }

    @Test
    public void AthenaGodpowerTestOpponentCanMoveUp() {
        athena.selectWorker(origin);
        athena.move(new Coordinates(11));
        opponent.selectWorker(originOpponent);
        assertTrue(opponent.move(new Coordinates(13)));
    }

    @Test
    public void AthenaGodpowerCanMoveUp() {
        athena.selectWorker(origin);
        athena.move(new Coordinates(12));
        athena.selectWorker(worker2);
        assertTrue(athena.move(new Coordinates(13)));
    }

    @Test
    public void AthenaGodpowerResetsProperly() {
        athena.selectWorker(origin);
        athena.move(new Coordinates(12));
        athena.move(new Coordinates(6));
        opponent.selectWorker(originOpponent);
        assertTrue(opponent.move(new Coordinates(12)));
    }
}
