package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;

/**
 * This class is the implementation of the divinity Artemis. Artemis can move twice before building, but not back to the
 * same {@code Square}. This behaviour is achieved with a custom implementation of {@code AbstractTurn}.
 *
 * @author Paolo Galli
 */
public class Artemis extends Divinity {

    private final String name = "Artemis";

    Coordinates startOfTurnSquare = null;

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

        @Override
        public boolean tryAction(Coordinates workerCoordinates, Action action, Coordinates squareCoordinates) {

            if (actionsTaken.isEmpty()) {
                if (!selectWorker(workerCoordinates))
                    return false;
                startOfTurnSquare = workerCoordinates;
            }

            if (availableActions.contains(action)) {
                switch (action) {
                    case MOVE:
                        if (actionsTaken.contains(Action.MOVE)) {
                            //if Artemis has already moved, check if she is moving back to the same square
                            if (!squareCoordinates.equals(startOfTurnSquare) && move(squareCoordinates)) {
                                actionsTaken.add(Action.MOVE);
                                availableActions.remove(Action.MOVE);
                                return true;
                            }
                        }
                        else if (move(squareCoordinates)) {
                            actionsTaken.add(Action.MOVE);
                            availableActions.add(Action.BUILD);
                            return true;
                        }
                        break;

                    case BUILD:
                        if (build(squareCoordinates)) {
                            actionsTaken.add(Action.BUILD);
                            availableActions.clear();
                            availableActions.add(Action.ENDTURN);
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
            startOfTurnSquare = null;
        }

        @Override
        public AbstractTurn copy() {
            return new Turn(this.availableActions, this.actionsTaken);
        }
    }
}
