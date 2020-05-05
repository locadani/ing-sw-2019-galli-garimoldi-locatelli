package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class ArtemisTurnTest {
    Artemis god = null;
    AbstractTurn turn = null;
    Worker worker = null;
    Square square = null;


    @Before
    public void setUp() {
        god = new ArtemisMock();
        turn = god.getTurn();
    }

    @Test
    public void possibleTurnsTest() {
        ArrayList<Action> turn1 = new ArrayList<Action>();
        turn1.add(Action.MOVE);
        turn1.add(Action.MOVE);
        turn1.add(Action.BUILD);
        turn1.add(Action.ENDTURN);
        ArrayList<Action> turn2 = new ArrayList<Action>();
        turn2.add(Action.MOVE);
        turn2.add(Action.BUILD);
        turn2.add(Action.ENDTURN);

        ArrayList<ArrayList<Action>> turns = new ArrayList<>();
        for(Action action : Action.values()){
            if(turn.tryAction(action, worker, square)) {
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
                if (tcopy.tryAction(action, worker, square)) {
                    // bifurcate
                    findPossibleTurns(tcopy, record);
                    tcopy = t.copy();
                }
            }
        }
        return record;
    }

}

class ArtemisMock extends Artemis {
    @Override
    public boolean move(Square destination) {
        return true;
    }

    @Override
    public boolean build(Square target) {
        return true;
    }

}