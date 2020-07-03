package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;

import java.util.List;

/**
 * This class is the implementation of the divinity Demeter. Demeter can build twice, but not on the same {@code Square}. <p>
 * This behaviour is achieved with a custom implementation of {@code AbstractTurn}.
 *
 * @author Paolo Galli
 */
public class Demeter extends Divinity {

    private final String name = "Demeter";

    Coordinates squareBuilt = null;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbstractTurn getTurn() {
        return new Demeter.Turn();
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
                        if (move(squareCoordinates)) {
                            actionsTaken.add(Action.MOVE);
                            availableActions.clear();
                            availableActions.add(Action.BUILD);
                            return true;
                        }
                        break;

                    case BUILD:
                        if (actionsTaken.contains(Action.BUILD)) {
                            if (!squareCoordinates.equals(squareBuilt) && build(squareCoordinates)) {
                                actionsTaken.add(Action.BUILD);
                                availableActions.remove(Action.BUILD);
                                return true;
                            }
                        } else if (build(squareCoordinates)) {
                            actionsTaken.add(Action.BUILD);
                            availableActions.add(Action.ENDTURN);
                            squareBuilt = squareCoordinates;
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
        public AbstractTurn copy() {
            return new Demeter.Turn(this.availableActions, this.actionsTaken);
        }
    }
}
