package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DefeatCheckerTest {
    Divinity apollo = null;
    Divinity prometheus = null;
    Player player1 = null;
    Player player2 = null;

    Board board = null;
    Coordinates origin = null;
    Coordinates originOpponent = null;
    Coordinates worker2 = null;
    DefeatChecker defeatChecker = null;

    @Before
    public void setUp() {
        player1 = new Player("a", 1);
        player2 = new Player("b", 2);
        apollo = DivinityFactory.create("Apollo");
        prometheus = DivinityFactory.create("Prometheus");
        board = new Board();
        apollo.setBoard(board);
        prometheus.setBoard(board);
        DivinityMediator divinityMediator = new DivinityMediator();
        divinityMediator = apollo.decorate(divinityMediator);
        SentinelDecorator mediator = new SentinelDecorator(divinityMediator);
        apollo.setDivinityMediator(mediator);
        prometheus.setDivinityMediator(mediator);

        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        defeatChecker = new DefeatChecker(playerList, board);

        Worker apolloWorker = new Worker(origin, player1);
        Worker prometheusWorker = new Worker(originOpponent, player2);

        player1.setDivinity(apollo);
        player1.addWorker(apolloWorker);
        player2.setDivinity(prometheus);
        player2.addWorker(prometheusWorker);

        origin = new Coordinates(7);
        originOpponent = new Coordinates(8);

        apollo.placeWorker(apolloWorker, origin);
        prometheus.placeWorker(prometheusWorker, originOpponent);
        board.getSquare(new Coordinates(12)).insert(new Dome());
        board.getSquare(new Coordinates(13)).insert(new Dome());
        board.getSquare(new Coordinates(6)).insert(new Dome());
        board.getSquare(new Coordinates(1)).insert(new Dome());
        board.getSquare(new Coordinates(2)).insert(new Dome());
        board.getSquare(new Coordinates(3)).insert(new Dome());
        board.getSquare(new Coordinates(11)).insert(new Dome());
    }

    @Test
    public void ApolloCanOnlyUseGodPowerTest() {
        try {
            defeatChecker.checkDefeat(apollo.getTurn(), player1);
            assertTrue(1<2);
        } catch (LossException e) {
            System.out.println(e.getLoser().getUsername());
        }
    }

    @Test
    public void AlreadyChosenWorkerTest()
    {
        AbstractTurn turn = apollo.getTurn();

        turn.tryAction(origin, Action.MOVE, new Coordinates(2));
        try {
            defeatChecker.checkDefeat(apollo.getTurn(), player1);
            assertTrue(1<2);
        } catch (LossException e) {
            System.out.println(e.getLoser().getUsername());
        }
    }

    @Test
    public void ApolloDefeatedTest() {
        prometheus.selectWorker(originOpponent);
        prometheus.move(new Coordinates(9));
        board.getSquare(new Coordinates(8)).insert(new Dome());
        try {
            defeatChecker.checkDefeat(apollo.getTurn(), player1);
        } catch (LossException e) {
            assertEquals(e.getLoser().getUsername(), player1.getUsername());
        }
    }

    @Test
    public void ApolloDefeatedNoSideEffectsTest() {
        Board boardCopy = new Board(board);
        prometheus.selectWorker(originOpponent);
        prometheus.move(new Coordinates(9));
        board.getSquare(new Coordinates(8)).insert(new Dome());
        try {
            defeatChecker.checkDefeat(apollo.getTurn(), player1);
        } catch (LossException e) {
            assertEquals(e.getLoser().getUsername(), player1.getUsername());
            assertTrue(TestHelperFunctions.boardEquals(boardCopy, board));
        }
    }

    @Test
    public void ApolloCanOnlyUseGodPowerNoSideEffectsTest() {
        try {
            Board boardCopy = new Board(board);
            defeatChecker.checkDefeat(apollo.getTurn(), player1);
            assertTrue(1<2);
            assertTrue(TestHelperFunctions.boardEquals(boardCopy, board));
        } catch (LossException e) {
            System.out.println(e.getLoser().getUsername());
        }
    }

    @Test
    public void NotDefeatedTest()
    {
        board.getSquare(new Coordinates(4)).insert(new Dome());
        board.getSquare(new Coordinates(5)).insert(new Dome());
        board.getSquare(new Coordinates(10)).insert(new Dome());
        board.getSquare(new Coordinates(10)).insert(new Dome());
        board.getSquare(new Coordinates(14)).insert(new Dome());
        board.getSquare(new Coordinates(15)).insert(new Dome());
        apollo.selectWorker(new Coordinates(7));
        apollo.move(new Coordinates(8));
        apollo.build(new Coordinates(9));
        apollo.move(new Coordinates(9));
        board.getSquare(new Coordinates(8)).insert(new Dome());
        try {

            defeatChecker.checkDefeat(apollo.getTurn(), player1);
            assertTrue(1<2);
        }
        catch (LossException e) {
            assertTrue(1<2);
            e.printStackTrace();
        }
    }
    //TODO test for no side-effects of defeatChecker

}

