package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.SentinelDecorator;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApolloTest {
    Divinity apollo = null;
    Board board = null;
    AbstractTurn turn;
    RequestedAction notPossibleAction = new RequestedAction(1, Action.BUILD, 2);
    RequestedAction moveFrom1to2 = new RequestedAction(1, Action.MOVE, 2);
    RequestedAction noWorkerMove = new RequestedAction(2, Action.MOVE, 2);
    RequestedAction buildOn6 = new RequestedAction(99, Action.BUILD, 6);
    RequestedAction endTurn = new RequestedAction(2, Action.ENDTURN, 6);
    Worker opponentWorker;

    @Before
    public void setUp() {
        Player player = new Player("a", 1);
        apollo = DivinityFactory.create("Apollo");
        board = new Board();
        apollo.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        SentinelDecorator mediator = new SentinelDecorator(divinityMediator);
        apollo.setDivinityMediator(mediator);
        player.setDivinity(apollo);

        Player opponent = new Player("opponent",2);
        Divinity opponentDivinity = DivinityFactory.create("Atlas");
        opponent.setDivinity(opponentDivinity);
        opponentDivinity.setBoard(board);
        opponentDivinity.setDivinityMediator(mediator);

        apollo.setDivinityMediator(mediator);
        opponentWorker = new Worker(new Coordinates(2), opponent);
        apollo.placeWorker(opponentWorker,new Coordinates(2));
        apollo.placeWorker(new Worker(new Coordinates(1), player), new Coordinates(1));
        Square twoBlocksSquare = board.getSquare(new Coordinates(4));
        twoBlocksSquare.insert(new Block());
        twoBlocksSquare.insert(new Block());
    }
    
    @Test
    public void moveTest() {
        turn = apollo.getTurn();
        apollo.selectWorker(new Coordinates(1));
        assertFalse(apollo.move(new Coordinates(1)));
        assertTrue(apollo.move(new Coordinates(2)));
        assertTrue(apollo.move(new Coordinates(3)));
        assertFalse(apollo.move(new Coordinates(4)));
    }
}