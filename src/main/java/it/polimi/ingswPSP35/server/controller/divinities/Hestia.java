package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;

import java.util.List;


/**
 * This class is the implementation of the divinity Hestia. Hestia can build twice, but the second time cannot be
 * a perimetral {@code Square}. <p>
 * This behaviour is achieved with a custom implementation of {@code AbstractTurn}.
 *
 * @author Paolo Galli
 */
public class Hestia extends Divinity {
    private static String name = "Hestia";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbstractTurn getTurn() {
        return new Hestia.Turn();
    }

    public boolean buildRestricted(Coordinates targetCoordinates) {
        if (!board.getSquare(targetCoordinates).isPerimetral()) {
            return super.build(targetCoordinates);
        } else return false;
    }

    private class Turn extends AbstractTurn {

        public Turn() {
            super();
        }

        private Turn(List<Action> availableActions, List<Action> actionsTaken) {
            super(availableActions, actionsTaken);
        }

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
                        //if Hestia has already built, check if she's trying to build on the perimeter
                        if (actionsTaken.contains(Action.BUILD)) {
                            if (buildRestricted(squareCoordinates)) {
                                actionsTaken.add(Action.BUILD);
                                availableActions.remove(Action.BUILD);
                                return true;
                            }
                        } else if (build(squareCoordinates)) {
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
        public AbstractTurn copy() {
            return new Hestia.Turn(this.availableActions, this.actionsTaken);
        }
    }
}

