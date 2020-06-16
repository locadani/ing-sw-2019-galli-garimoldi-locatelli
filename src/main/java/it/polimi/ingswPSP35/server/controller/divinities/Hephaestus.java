package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;

import java.util.ArrayList;

public class Hephaestus extends Divinity {

    private final String name = "Hephaestus";
    private Coordinates squareBuilt;

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

                    case GODPOWER:
                        return false;

                    case ENDTURN:
                        reset();
                        return true;
                }
            }
            return false;
        }

        @Override
        public AbstractTurn copy() {
            return new Hephaestus.Turn(this.availableActions, this.actionsTaken);
        }
    }
}
