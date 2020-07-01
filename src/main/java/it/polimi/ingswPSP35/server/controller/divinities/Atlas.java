package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Dome;
import it.polimi.ingswPSP35.server.model.Square;

import java.util.ArrayList;
import java.util.List;

public class Atlas extends Divinity {
    private final String name = "Atlas";

    @Override
    public String getName() {
        return name;
    }

    public boolean buildDome(Coordinates targetCoordinates) {
        Square target = board.getSquare(targetCoordinates);
        Square workerSquare = board.getSquare(selectedWorker.getCoordinates());
        if (canBuild(selectedWorker, workerSquare, target)) {
            target.insert(new Dome());
            board.setChangedSquares(List.of(target));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AbstractTurn getTurn() {
        return new Atlas.Turn();
    }

    public class Turn extends AbstractTurn {

        public Turn() {
            super();
        }

        private Turn(List<Action> availableActions, List<Action> actionsTaken){
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
                            availableActions.add(Action.GODPOWER);
                            return true;
                        }
                        break;

                    case BUILD:
                        if (build(squareCoordinates)) {
                            availableActions.clear();
                            actionsTaken.add(Action.BUILD);
                            availableActions.add(Action.ENDTURN);
                            return true;
                        }
                        break;

                    case GODPOWER:
                        if (buildDome(squareCoordinates)) {
                            availableActions.clear();
                            actionsTaken.add(Action.GODPOWER);
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
            return new Atlas.Turn(availableActions, actionsTaken);
        }
    }
}
