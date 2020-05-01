package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;


public class Artemis extends Divinity {

    private final String name = "Artemis";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbstractTurn getTurn() {
        return new Artemis.Turn();
    }

    private class Turn extends AbstractTurn{

        public Turn() {
            super();
        }

        private Turn(List<Action> availableActions, List<Action> actionsTaken){
            super(availableActions,actionsTaken);
        }

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


        @Override
        public AbstractTurn copy() {
            return new Turn(this.availableActions, this.actionsTaken);
        }
    }
}
