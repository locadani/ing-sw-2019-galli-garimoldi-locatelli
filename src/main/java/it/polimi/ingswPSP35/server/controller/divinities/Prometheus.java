package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;

public class Prometheus extends Divinity {

    private final String name = "Prometheus";

    public String getName() {
        return this.name;
    }

    public boolean restrictedMove(Square destination) {
        Square origin = board.getSquare(selectedWorker.getCoordinates());
        if (canMove(selectedWorker, origin, destination)
                && destination.getHeight() <= origin.getHeight()) {
            origin.removeTop();
            destination.insert(selectedWorker);
            selectedWorker.setCoordinates(destination.getCoordinates());
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

        public Turn() {
            super();
        }

        private Turn(ArrayList<Action> availableActions, ArrayList<Action> actionsTaken) {
            super(availableActions, actionsTaken);
        }

        public boolean tryAction(Action action, Worker worker, Square square) {
            if (availableActions.contains(action)) {
                switch (action) {
                    case MOVE:
                        if (actionsTaken.isEmpty()) {
                            if (move(square)) {
                                selectWorker(worker);
                                actionsTaken.add(Action.MOVE);
                                availableActions.remove(Action.MOVE);
                                return true;
                            }
                        } else {
                            if (restrictedMove(square)) {
                                actionsTaken.add(Action.MOVE);
                                availableActions.remove(Action.MOVE);
                                availableActions.add(Action.BUILD);
                                return true;
                            }
                        }
                    case BUILD:
                        if(actionsTaken.isEmpty()) {
                            selectWorker(worker);
                        }
                        if (build(square)) {
                            availableActions.remove(Action.BUILD);
                            actionsTaken.add(Action.BUILD);
                            if (actionsTaken.contains(Action.MOVE)) {
                                availableActions.clear();
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

        @Override
        public AbstractTurn copy() {
            return new Prometheus.Turn(this.availableActions, this.actionsTaken);
        }
    }
}
