package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DefeatCheckTest {
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
        board = new DebugBoard();
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
        //TODO how do we test with exceptions?
    }
    
    @Test
    public void ApolloDefeatedTest() {
        prometheus.selectWorker(originOpponent);
        prometheus.move(new Coordinates(9));
        board.getSquare(new Coordinates(8)).insert(new Dome());
        ((DebugBoard) board).printBoard();
        try {
            defeatChecker.checkDefeat(apollo.getTurn(), player1);
        } catch (LossException e) {
            assertEquals(e.getLoser().getUsername(), player1.getUsername());
        }
    }

}

