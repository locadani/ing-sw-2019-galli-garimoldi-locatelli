package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;

public class Prometheus extends Divinity {

    private final String name = "Prometheus";

    public String getName() {
        return this.name;
    }

    public boolean restrictedMove(Square destination) {
        Square origin = board.getSquare(selectedWorker.getX(), selectedWorker.getY());
        if (canMove(selectedWorker, origin, destination)
                && destination.getHeight() <= origin.getHeight()) {
            origin.removeTop();
            destination.insert(selectedWorker);
            selectedWorker.setX(destination.getX());
            selectedWorker.setY(destination.getY());
            checkWin(selectedWorker, destination, origin);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public AbstractTurn getTurn() {
        return new Prometheus.Turn();
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
                        if (actionsTaken.contains(Action.BUILD)) {
                            if (restrictedMove(square)) {
                                selectWorker(worker);
                                actionsTaken.add(Action.MOVE);
                                availableActions.clear();
                                availableActions.add(Action.ENDTURN);
                                return true;
                            }
                        } else if (move(square)) {
                            selectWorker(worker);
                            actionsTaken.add(Action.MOVE);
                            availableActions.remove(Action.MOVE);
                            return true;
                        }
                    case BUILD:
                        if (build(square)) {
                            availableActions.remove(Action.MOVE);
                            actionsTaken.add(Action.BUILD);
                            if (!actionsTaken.contains(Action.MOVE)) {
                                availableActions.add(Action.ENDTURN);
                            }
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
            availableActions.add(Action.BUILD);
            selectWorker(null);
        }

        public List<Action> getActionsTaken() {
            return actionsTaken;
        }

        public List<Action> getAvailableActions() {
            return availableActions;
        }

        @Override
        public AbstractTurn copy() {
            return new Prometheus.Turn(this.availableActions, this.actionsTaken);
        }
    }
}
