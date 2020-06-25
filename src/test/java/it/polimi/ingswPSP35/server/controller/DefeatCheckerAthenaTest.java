package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.client.gui.Request;
import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DefeatCheckerAthenaTest {

    Divinity athena = null;
    Divinity demeter = null;

    Board board = null;

    Player player2 = null;

    DefeatChecker defeatChecker = null;

    Worker athenaWorker = null;

    @Before
    public void setUp() {
        Player player1 = new Player("a", 1);
        player2 = new Player("b", 2);
        athena = DivinityFactory.create("Athena");
        demeter = DivinityFactory.create("Demeter");
        board = new Board();
        athena.setBoard(board);
        demeter.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        divinityMediator = athena.decorate(divinityMediator);
        SentinelDecorator mediator = new SentinelDecorator(divinityMediator);
        athena.setDivinityMediator(mediator);
        demeter.setDivinityMediator(mediator);

        ArrayList<Player> playerList = new ArrayList<>(List.of(player1, player1));

        defeatChecker = new DefeatChecker(playerList, board);

        board.getSquare(new Coordinates(3)).insert(new Block());
        board.getSquare(new Coordinates(5)).insert(new Block());
        board.getSquare(new Coordinates(5)).insert(new Block());
        board.getSquare(new Coordinates(8)).insert(new Block());
        board.getSquare(new Coordinates(9)).insert(new Block());
        board.getSquare(new Coordinates(10)).insert(new Block());
        board.getSquare(new Coordinates(15)).insert(new Block());

        player1.setDivinity(athena);
        athenaWorker = new Worker(player1);
        player1.addWorker(athenaWorker);
        athena.placeWorker(athenaWorker, new Coordinates(14));

        player2.setDivinity(demeter);
        Worker demeterWorker1 = new Worker(player2);
        player2.addWorker(demeterWorker1);
        athena.placeWorker(demeterWorker1, new Coordinates(5));

        Worker demeterWorker2 = new Worker(player2);
        player2.addWorker(demeterWorker2);
        athena.placeWorker(demeterWorker2, new Coordinates(4));

    }

    @Test
    public void DefeatCheckerWorksAfterFailedActionTest() {
        athena.selectWorker(athenaWorker.getCoordinates());
        athena.move(new Coordinates(15));
        TestHelperFunctions.printBoard(board);


        AbstractTurn demeterTurn = demeter.getTurn();

        RequestedAction moveFrom4to8 = new RequestedAction(4, Action.MOVE, 8);
        RequestedAction moveFrom5to10 = new RequestedAction(5, Action.MOVE, 10);

        assertFalse(demeterTurn.tryAction(moveFrom4to8.getWorker(), moveFrom4to8.getAction(), moveFrom4to8.getSquare()));

        try {
            defeatChecker.checkDefeat(demeterTurn, player2);
        } catch (LossException e) {
            fail();
        }
    }
}
