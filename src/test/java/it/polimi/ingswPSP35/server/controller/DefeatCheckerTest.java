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
import java.util.List;

import static org.junit.Assert.*;

public class DefeatCheckerTest {
    private Divinity apollo = null;
    private Divinity prometheus = null;
    private Player player = null;
    private Player opponent = null;

    private Board board = null;
    private Coordinates origin = null;
    private Coordinates originOpponent = null;
    private Coordinates worker2 = null;
    private DefeatChecker defeatChecker = null;

    @Before
    public void setUp() {
        
        /*
        Player worker is in cell 7
        Opponent worker in cell 8
        Domes on floor in cells 1, 2, 3, 6, 11, 12, 13
        When Player, using Apollo godpower, swaps workers positions the opponent can't move
        and will lose
         */
        player = new Player("a", 1);
        opponent = new Player("b", 2);
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

        ArrayList<Player> playerList = new ArrayList<>(List.of(player, player));

        defeatChecker = new DefeatChecker(playerList, board);

        Worker apolloWorker = new Worker(origin, player);
        Worker prometheusWorker = new Worker(originOpponent, opponent);

        player.setDivinity(apollo);
        player.addWorker(apolloWorker);
        opponent.setDivinity(prometheus);
        opponent.addWorker(prometheusWorker);

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
            defeatChecker.checkDefeat(apollo.getTurn(), player);
        } catch (LossException e) {
            fail();
        }
    }

    @Test
    public void AlreadyChosenWorkerTest()
    {
        AbstractTurn turn = apollo.getTurn();

        turn.tryAction(origin, Action.MOVE, new Coordinates(2));
        try {
            defeatChecker.checkDefeat(apollo.getTurn(), player);
        } catch (LossException e) {
            fail();
        }
    }

    @Test
    public void ApolloDefeatedTest() {
        prometheus.selectWorker(originOpponent);
        prometheus.move(new Coordinates(9));
        board.getSquare(new Coordinates(8)).insert(new Dome());
        try {
            defeatChecker.checkDefeat(apollo.getTurn(), player);
        } catch (LossException e) {
            assertEquals(e.getLoser().getUsername(), player.getUsername());
        }
    }

    @Test
    public void ApolloDefeatedNoSideEffectsTest() {
        prometheus.selectWorker(originOpponent);
        prometheus.move(new Coordinates(9));
        board.getSquare(new Coordinates(8)).insert(new Dome());
        Board boardCopy = new Board(board);
        try {
            defeatChecker.checkDefeat(apollo.getTurn(), player);
        } catch (LossException e) {
            assertEquals(e.getLoser().getUsername(), player.getUsername());
            assertTrue(TestHelperFunctions.boardEquals(boardCopy, board));
        }
    }

    @Test
    public void ApolloCanOnlyUseGodPowerNoSideEffectsTest() {
        try {
            Board boardCopy = new Board(board);
            defeatChecker.checkDefeat(apollo.getTurn(), player);
            assertTrue(TestHelperFunctions.boardEquals(boardCopy, board));
        } catch (LossException e) {
            fail();
        }
    }

    @Test
    public void DefeatedOnSecondMoveTest()
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
            defeatChecker.checkDefeat(apollo.getTurn(), player);
            fail();
        }
        catch (LossException e) {
            //ignore
        }
    }
}

