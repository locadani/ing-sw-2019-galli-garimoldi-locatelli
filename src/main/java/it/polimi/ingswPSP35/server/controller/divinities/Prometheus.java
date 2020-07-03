package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the implementation of the divinity Prometheus. Prometheus can build both before and after moving if he
 * doesn't move up. <p>
 * This behaviour is achieved with a custom implementation of {@code AbstractTurn} and a special method.
 *
 * @author Paolo Galli
 */
public class Prometheus extends Divinity {

    private final String name = "Prometheus";

    public String getName() {
        return this.name;
    }

    /**
     * Attempts to move {@code selectedWorker} to Square {@code destination}. If the attempt is successful, the board
     * is notified of what squares have been affected by the move action. <p>
     * Unlike the {@code move} method, this does not allow {@code selectedWorker} to move up.
     *
     * @param destinationCoordinates the Square one wishes to move to
     * @return true if the move action attempt was successful
     */
    public boolean restrictedMove(Coordinates destinationCoordinates) {
        Square destination = board.getSquare(destinationCoordinates);
        Square origin = board.getSquare(selectedWorker.getCoordinates());
        if (canMove(selectedWorker, origin, destination)
                && destination.getHeight() <= origin.getHeight()) {
            origin.removeTop();
            destination.insert(selectedWorker);
            selectedWorker.setCoordinates(destination.getCoordinates());
            board.setChangedSquares(List.of(origin, destination));
            checkWin(selectedWorker, destination, origin);
            board.setChangedSquares(List.of(destination, origin));
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

        private Turn(List<Action> availableActions, List<Action> actionsTaken) {
            super(availableActions, actionsTaken);
        }

        @Override
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

        @Override
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
