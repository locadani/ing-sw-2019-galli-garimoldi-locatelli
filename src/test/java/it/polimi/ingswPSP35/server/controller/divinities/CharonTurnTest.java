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



    @Before
    public void setUp() {
        god = new CharonMock();
        turn = god.getTurn();
    }

    @Test
    public void possibleTurnsTest() {
        ArrayList<Action> turn1 = new ArrayList<Action>(List.of(Action.GODPOWER, Action.MOVE, Action.BUILD, Action.ENDTURN));
        ArrayList<Action> turn2 = new ArrayList<Action>(List.of(Action.MOVE, Action.BUILD, Action.ENDTURN));

        List<List<Action>> turns = new ArrayList<>();
        for(Action action : Action.values()){
            if(turn.tryAction(worker, action, square)) {
                ArrayList<List<Action>> candidate = findPossibleTurns(turn.copy(), new ArrayList<>());
                if (candidate != null) turns.addAll(candidate);
                turn.reset();
            }
        }
        assertTrue (turns.size() == 2
                && (turn1.equals(turns.get(0)) || turn1.equals(turns.get(1)))
                && (turn2.equals(turns.get(0)) || turn2.equals(turns.get(1))));
    }


    public ArrayList<List<Action>> findPossibleTurns (AbstractTurn t, ArrayList<List<Action>> record) {
        List<Action> availableActions = t.getAvailableActions();
        if (availableActions.contains(Action.ENDTURN)) {
            List<Action> sequence = t.getActionsTaken();
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
