package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class PrometheusTurnTest {
    Prometheus god = null;
    AbstractTurn turn = null;
    Worker worker = null;
    Square square = null;


    @Before
    public void setUp() {
        god = new PrometheusMock();
        turn = god.getTurn();
    }

    @Test
    public void possibleTurnsTest() {
        ArrayList<Action> turn1 = new ArrayList<Action>();
        turn1.add(Action.BUILD);
        turn1.add(Action.MOVE);
        turn1.add(Action.BUILD);
        turn1.add(Action.ENDTURN);
        ArrayList<Action> turn2 = new ArrayList<Action>();
        turn2.add(Action.MOVE);
        turn2.add(Action.BUILD);
        turn2.add(Action.ENDTURN);

        ArrayList<Action> a = new ArrayList<>();
        ArrayList<ArrayList<Action>> turns = new ArrayList<>();
        for(Action action : Action.values()){
            if(turn.tryAction(action, worker, square)) {
                a.add(action);
                ArrayList<Action> candidate = findPossibleTurns(turn.copy(), (ArrayList<Action>) a.clone());
                if (candidate != null) turns.add(candidate);
                a.clear();
                turn.reset();
            }
        }
    assertTrue ((turn1.equals(turns.get(0)) || turn1.equals(turns.get(1)))
            && (turn2.equals(turns.get(0)) || turn2.equals(turns.get(1))));
    }

    public ArrayList<Action> findPossibleTurns (AbstractTurn t, ArrayList<Action> a) {
        if (a.contains(Action.ENDTURN))  return a;
        for(Action action : Action.values()){
            if(t.tryAction(action, worker, square)) {
                a.add(action);
                a = findPossibleTurns(t.copy(), a);
                if (a.contains(Action.ENDTURN)) return a;
            }
        }
        return null;
    }

}

class PrometheusMock extends Prometheus {
    @Override
    public boolean move(Square destination) {
        return true;
    }

    @Override
    public boolean build(Square target) {
        return true;
    }

    @Override
    public boolean restrictedMove(Square destination) {
        return true;
    }
}