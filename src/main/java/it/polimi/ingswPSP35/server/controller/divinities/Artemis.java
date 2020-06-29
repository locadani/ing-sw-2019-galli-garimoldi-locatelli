package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;

import java.util.ArrayList;
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

        public boolean tryAction(Coordinates workerCoordinates, Action action, Coordinates squareCoordinates) {

            if (actionsTaken.isEmpty())
                if (!selectWorker(workerCoordinates))
                    return false;

            if (availableActions.contains(action)) {
                switch (action) {
                    case MOVE:
                        if (move(squareCoordinates)) {
                            if (actionsTaken.contains(Action.MOVE))
                            {
                                actionsTaken.add(Action.MOVE);
                                availableActions.remove(Action.MOVE);
                            }
                            else {
                                actionsTaken.add(Action.MOVE);
                                availableActions.add(Action.BUILD);
                            }
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
        public AbstractTurn copy() {
            return new Turn(this.availableActions, this.actionsTaken);
        }
    }
}
