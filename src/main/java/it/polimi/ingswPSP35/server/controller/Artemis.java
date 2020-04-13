package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;


public class Artemis extends Divinity {

    private String Name = "Artemis";

    @Override
    public void playTurn() {
        //TODO decide how to implement turn structure and client interaction
    }


    public class Turn {
        private List<Action> availableActions;
        private List<Action> actionsTaken;

        //TODO define case-specific exceptions?
        public boolean tryAction(Action action, Worker worker, Square square) {
            if (availableActions.contains(action)) {
                switch (action) {
                    case MOVE:
                        if (move(square)) {
                            if (actionsTaken.contains(Action.MOVE))
                            {
                                availableActions.remove(Action.MOVE);
                            }
                            else {
                                selectWorker(worker);
                                actionsTaken.add(Action.MOVE);
                                availableActions.add(Action.BUILD);
                            }
                            return true;
                        }
                    case BUILD:
                        if (build(square)) {
                            availableActions.clear();
                            actionsTaken.add(Action.BUILD);
                            availableActions.add(Action.ENDTURN);
                            return true;
                        }
                    case GODPOWER:
                        return false;
                    case ENDTURN:
                        reset();
                        return true;
                }
            }
            return false;
        }

        public void reset() {
            availableActions.clear();
            actionsTaken.clear();
            availableActions.add(Action.MOVE);
            selectWorker(null);
        }
    }
}
