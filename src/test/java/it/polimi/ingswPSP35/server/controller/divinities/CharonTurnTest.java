package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.Winner;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CharonTurnTest {
    Charon god = null;
    AbstractTurn turn = null;
    Coordinates worker = null;
    Coordinates square = null;

    Player player, opponent;
    Board board;
    Winner winner;
    DivinityMediator divinityMediator;
    Worker playerWorker, playerSecondWorker, opponentWorker;

    @Before
    public void setUp() {
        god = new CharonMock();
        turn = god.getTurn();

        winner = new Winner();
        board = new Board();
        player = new Player("Player", 1);
        playerWorker = new Worker(new Coordinates(1), player);
        playerSecondWorker = new Worker(new Coordinates(13), player);
        player.setDivinity(DivinityFactory.create("Charon"));
        player.getDivinity().setWinner(winner);
        player.getDivinity().setBoard(board);
        player.getDivinity().placeWorker(playerWorker, playerWorker.getCoordinates());
        player.getDivinity().selectWorker(playerWorker.getCoordinates());
        player.addWorker(playerWorker);
        player.getDivinity().placeWorker(playerSecondWorker, playerSecondWorker.getCoordinates());
        player.addWorker(playerSecondWorker);

        opponent = new Player("Opponent", 2);
        opponentWorker = new Worker(new Coordinates(7), opponent);
        opponent.setDivinity(DivinityFactory.create("Pan"));
        opponent.getDivinity().setWinner(winner);
        opponent.getDivinity().setBoard(board);
        opponent.getDivinity().placeWorker(opponentWorker, opponentWorker.getCoordinates());
        opponent.getDivinity().selectWorker(opponentWorker.getCoordinates());
        opponent.addWorker(opponentWorker);

        divinityMediator = new DivinityMediator();
        divinityMediator = player.getDivinity().decorate(divinityMediator);
        divinityMediator = opponent.getDivinity().decorate(divinityMediator);
        player.getDivinity().setDivinityMediator(divinityMediator);
        opponent.getDivinity().setDivinityMediator(divinityMediator);
    }

    @Test
    public void possibleTurnsTest() {
        ArrayList<Action> turn1 = new ArrayList<Action>(List.of(Action.GODPOWER, Action.MOVE, Action.BUILD, Action.ENDTURN));
        ArrayList<Action> turn2 = new ArrayList<Action>(List.of(Action.MOVE, Action.BUILD, Action.ENDTURN));

        ArrayList<ArrayList<Action>> turns = new ArrayList<>();
        for(Action action : Action.values()){
            if(turn.tryAction(worker, action, square)) {
                ArrayList<ArrayList<Action>> candidate = findPossibleTurns(turn.copy(), new ArrayList<>());
                if (candidate != null) turns.addAll(candidate);
                    turn.reset();
            }
        }
        assertTrue (turns.size() == 2
                && (turn1.equals(turns.get(0)) || turn1.equals(turns.get(1)))
                && (turn2.equals(turns.get(0)) || turn2.equals(turns.get(1))));
    }


    public ArrayList<ArrayList<Action>> findPossibleTurns (AbstractTurn t, ArrayList<ArrayList<Action>> record) {
        ArrayList<Action> availableActions = t.getAvailableActions();
        if (availableActions.contains(Action.ENDTURN)) {
            ArrayList<Action> sequence = t.getActionsTaken();
            sequence.add(Action.ENDTURN);
            record.add(sequence);
        }
        AbstractTurn tcopy = t.copy();
        for (Action action : Action.values()) {
            if (action != Action.ENDTURN) {
                if (tcopy.tryAction(worker, action, square)) {
                    // bifurcate
                    findPossibleTurns(tcopy, record);
                    tcopy = t.copy();
                }
            }
        }
        return record;
    }

    @Test
    public void selectCellWithoutWorkerTest()
    {
        turn = player.getDivinity().getTurn();
        assertFalse(turn.tryAction(new Coordinates(10), Action.BUILD,new Coordinates(3)));
    }

    @Test
    public void charonCanUseGodpower()
    {
        turn = player.getDivinity().getTurn();
        player.getDivinity().selectWorker(playerSecondWorker.getCoordinates());
        assertTrue(turn.tryAction(playerSecondWorker.getCoordinates(), Action.GODPOWER, opponentWorker.getCoordinates()));
    }

    @Test
    public void charonCannotUseGodpower()
    {
        turn = player.getDivinity().getTurn();
        assertFalse(turn.tryAction(playerWorker.getCoordinates(), Action.GODPOWER, opponentWorker.getCoordinates()));
    }

    @Test
    public void notAvailableActionTest()
    {
        turn = player.getDivinity().getTurn();
        assertFalse(turn.tryAction(playerWorker.getCoordinates(), Action.BUILD, new Coordinates(2)));
    }

    @Test
    public void endTurnTest()
    {
        turn = player.getDivinity().getTurn();
        turn.tryAction(playerWorker.getCoordinates(), Action.MOVE,new Coordinates(2));
        turn.tryAction(playerWorker.getCoordinates(), Action.BUILD,new Coordinates(3));
        assertTrue(turn.tryAction(playerWorker.getCoordinates(), Action.ENDTURN, new Coordinates(3)));
    }
}

class CharonMock extends Charon {
    @Override
    public boolean move(Coordinates destination) {
        return true;
    }

    @Override
    public boolean build(Coordinates target) {
        return true;
    }

    @Override
    public boolean godpower(Coordinates target) {
        return true;
    }

    @Override
    public boolean selectWorker(Coordinates w){
        return true;
    }

}
