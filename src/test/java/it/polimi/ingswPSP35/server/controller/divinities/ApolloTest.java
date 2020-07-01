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
    private Divinity apollo = null;
    private Board board = null;
    private AbstractTurn turn;
    private RequestedAction notPossibleAction = new RequestedAction(1, Action.BUILD, 2);
    private RequestedAction moveFrom1to2 = new RequestedAction(1, Action.MOVE, 2);
    private RequestedAction noWorkerMove = new RequestedAction(2, Action.MOVE, 2);
    private RequestedAction buildOn6 = new RequestedAction(99, Action.BUILD, 6);
    private RequestedAction endTurn = new RequestedAction(2, Action.ENDTURN, 6);
    private Worker opponentWorker;

    @Before
    public void setUp() {

        /*Player worker on cell 1
            opponent worker on cell 2
            height two tower in cell 3
         */

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
        Square twoBlocksSquare = board.getSquare(new Coordinates(3));
        twoBlocksSquare.insert(new Block());
        twoBlocksSquare.insert(new Block());
        turn = apollo.getTurn();
        apollo.selectWorker(new Coordinates(1));
    }


    @Test
    public void moveOnOpponentWorkerCellTest() {
        assertTrue(apollo.move(new Coordinates(2)));
    }

    @Test
    public void cannotMoveOnHeightTwoCellTest()
    {
        apollo.move(new Coordinates(2));
        assertFalse(apollo.move(new Coordinates(3)));
    }

    //Worker is placed on first cell and tries to move on the cell he is on
    @Test
    public void moveOnSameSquareTest()
    {
        assertFalse(apollo.move(new Coordinates(1)));
    }
}