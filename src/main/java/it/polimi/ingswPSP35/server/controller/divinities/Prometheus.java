package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;

import java.util.ArrayList;

public class Prometheus extends Divinity {

    private final String name = "Prometheus";

    public String getName() {
        return this.name;
    }

    public boolean restrictedMove(Coordinates destinationCoordinates) {
        Square destination = board.getSquare(destinationCoordinates);
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

        public boolean tryAction(Coordinates workerCoordinates, Action action, Coordinates squareCoordinates) {

            if (actionsTaken.isEmpty())
                if (!selectWorker(workerCoordinates))
                    return false;

            if (availableActions.contains(action)) {
                switch (action) {
                    case MOVE:
                        if (actionsTaken.isEmpty()) {
                            if (move(squareCoordinates)) {
                                actionsTaken.add(Action.MOVE);
                                availableActions.remove(Action.MOVE);
                                return true;
                            }
                        } else {
                            if (restrictedMove(squareCoordinates)) {
                                actionsTaken.add(Action.MOVE);
                                availableActions.remove(Action.MOVE);
                                availableActions.add(Action.BUILD);
                                return true;
                            }
                        }
                        break;

                    case BUILD:
                        if (build(squareCoordinates)) {
                            availableActions.remove(Action.BUILD);
                            actionsTaken.add(Action.BUILD);
                            if (actionsTaken.contains(Action.MOVE)) {
                                availableActions.clear();
                                availableActions.add(Action.ENDTURN);
                            }
                            return true;
                        }
                        break;

                    case ENDTURN:
                        reset();
                        return true;
                }
            }
            return false;
        }

        public void reset() {
            super.reset();
            availableActions.add(Action.BUILD);
        }

        @Override
        public AbstractTurn copy() {
            return new Prometheus.Turn(this.availableActions, this.actionsTaken);
        }
    }
}
