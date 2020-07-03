package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.DivinityMediatorDecorator;
import it.polimi.ingswPSP35.server.controller.SentinelDecorator;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AthenaGodpowerTest {

    private Divinity athena = null;
    private Divinity opponentDivinity = null;
    private DivinityMediator mediator = null;

    private Board board = null;
    private Coordinates origin = null;
    private Coordinates originOpponent = null;
    private Coordinates worker2 = null;

    @Before
    public void setUp() {
        /*
            Player workers are in cells 7 and 17
            opponent worker is in cell 8
         */

        Player player = new Player("a", 1);
        Player opponent = new Player("b", 2);
        athena = DivinityFactory.create("Athena");
        opponentDivinity = DivinityFactory.create("Demeter");
        board = new Board();
        athena.setBoard(board);
        opponentDivinity.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        divinityMediator = athena.decorate(divinityMediator);
        mediator = new SentinelDecorator(divinityMediator);
        athena.setDivinityMediator(mediator);
        opponentDivinity.setDivinityMediator(mediator);

        player.setDivinity(athena);
        opponent.setDivinity(opponentDivinity);

        origin = new Coordinates(7);
        originOpponent = new Coordinates(8);
        worker2 = new Coordinates(17);

        athena.placeWorker(new Worker(origin, player), origin);
        athena.placeWorker(new Worker(worker2, player), worker2);
        opponentDivinity.placeWorker(new Worker(originOpponent, opponent), originOpponent);
        board.getSquare(new Coordinates(12)).insert(new Block());
        board.getSquare(new Coordinates(13)).insert(new Block());
        board.getSquare(new Coordinates(6)).insert(new Block());
    }

    @Test
    public void AthenaGodpowerOpponentCantMoveUpTest() {
        athena.selectWorker(origin);
        athena.move(new Coordinates(12));
        opponentDivinity.selectWorker(originOpponent);
        assertFalse(opponentDivinity.move(new Coordinates(13)));
    }

    @Test
    public void OpponentCanMoveUpTest() {
        athena.selectWorker(origin);
        athena.move(new Coordinates(11));
        opponentDivinity.selectWorker(originOpponent);
        assertTrue(opponentDivinity.move(new Coordinates(13)));
    }

    @Test
    public void AthenaNotAffectedByHerGodpowerTest() {
        athena.selectWorker(origin);
        athena.move(new Coordinates(12));
        athena.selectWorker(worker2);
        assertFalse(athena.move(new Coordinates(20)));
        assertTrue(athena.move(new Coordinates(13)));
    }

    @Test
    public void AthenaGodpowerResetsProperlyTest() {
        //move up athena
        athena.selectWorker(origin);
        athena.move(new Coordinates(12));

        //attempt to move up with other divinity
        opponentDivinity.selectWorker(originOpponent);
        assertFalse(opponentDivinity.move(new Coordinates(13)));

        //move down with Athena
        athena.move(new Coordinates(18));

        //move up with other divinity
        opponentDivinity.selectWorker(originOpponent);
        assertTrue(opponentDivinity.move(new Coordinates(12)));
    }

    @Test
    public void AthenaGodpowerDoesntWorkAfterUndecorationOfMediator() {
        ((DivinityMediatorDecorator) mediator).removeDecorator(athena.getName());
        opponentDivinity.selectWorker(originOpponent);
        assertTrue(opponentDivinity.move(new Coordinates(12)));
    }
}
