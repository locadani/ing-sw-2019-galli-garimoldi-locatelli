package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;

public class Demeter extends Divinity {

    private final String name = "Demeter";
    private Square squareBuilt;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbstractTurn getTurn() {
        return new Demeter.Turn();
    }

    private class Turn extends AbstractTurn {
        private List<Action> availableActions;
        private List<Action> actionsTaken;

        public Turn() {
            reset();
        }

        private Turn(List<Action> availableActions, List<Action> actionsTaken) {
            this.availableActions = List.copyOf(availableActions);
            this.actionsTaken = List.copyOf(actionsTaken);
        }

        public boolean tryAction(Action action, Worker worker, Square square) {
            if (availableActions.contains(action)) {
                switch (action) {
                    case MOVE:
                        if (move(square)) {
                            selectWorker(worker);
                            actionsTaken.add(Action.MOVE);
                            availableActions.clear();
                            availableActions.add(Action.BUILD);
                            return true;
                        }
                    case BUILD:
                        //if Demeter has already built, check if she's trying to build on the same square
                        if (actionsTaken.contains(Action.BUILD)) {
                            if(square != squareBuilt && build(square)) {
                                actionsTaken.add(Action.BUILD);
                                availableActions.remove(Action.BUILD);
                                return true;
                            }
                        } else if (build(square)) {
                            squareBuilt = square;
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

        public List<Action> getActionsTaken() {
            return List.copyOf(actionsTaken);
        }

        public List<Action> getAvailableActions() {
            return List.copyOf(availableActions);
        }

        @Override
        public AbstractTurn copy() {
            return new Demeter.Turn(this.availableActions, this.actionsTaken);
        }
    }
}
