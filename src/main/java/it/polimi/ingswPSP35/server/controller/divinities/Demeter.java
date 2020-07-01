package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class Demeter extends Divinity {

    private final String name = "Demeter";

    Square squareBuilt = null;

    @Override
    public boolean build(Coordinates targetCoordinates) {
        if (super.build(targetCoordinates)) {
            if (squareBuilt == null)
                squareBuilt = board.getSquare(targetCoordinates);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBuild(Worker worker, Square workerSquare, Square target) {
        if (super.canBuild(worker, workerSquare, target)) {
            if (squareBuilt != null)
                return squareBuilt != target;
            return true;
        }
        return false;
    }

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
                        if (build(squareCoordinates)) {
                            if (actionsTaken.contains(Action.BUILD))
                                availableActions.remove(Action.BUILD);
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
            return new Demeter.Turn(this.availableActions, this.actionsTaken);
        }
    }
}
