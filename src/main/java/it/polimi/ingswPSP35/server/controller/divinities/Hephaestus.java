package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;

import java.util.List;

/**
 * This class is the implementation of the divinity Hephaestus. Hephaestus can build twice on the same {@code Square}. <p>
 * This behaviour is achieved with a custom implementation of {@code AbstractTurn}.
 *
 * @author Paolo Galli
 */
public class Hephaestus extends Divinity {

    private final String name = "Hephaestus";

    Coordinates squareBuilt;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbstractTurn getTurn() {
        return new Hephaestus.Turn();
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
                        Square square = board.getSquare(squareCoordinates);
                        //if Hephaestus has already built, check if he's trying to build on the same square
                        if (actionsTaken.contains(Action.BUILD)) {
                            if(squareCoordinates.equals(squareBuilt) && square.getHeight()<= 2 && build(squareCoordinates)) {
                                actionsTaken.add(Action.BUILD);
                                availableActions.remove(Action.BUILD);
                                return true;
                            }
                        } else if (build(squareCoordinates)) {
                            squareBuilt = squareCoordinates;
                            actionsTaken.add(Action.BUILD);
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
            squareBuilt = null;
        }

        @Override
        public AbstractTurn copy() {
            return new Hephaestus.Turn(this.availableActions, this.actionsTaken);
        }
    }
}
