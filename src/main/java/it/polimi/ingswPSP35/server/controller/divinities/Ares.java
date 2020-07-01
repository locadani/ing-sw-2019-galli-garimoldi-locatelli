package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class Ares extends Divinity {
    private static String name = "Ares";
    private List<Worker> workerList = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean placeWorker(Worker worker, Coordinates coordinates) {
        if (super.placeWorker(worker, coordinates)) {
            workerList.add(worker);
            return true;
        } else return false;
    }

    public boolean godpower(Coordinates target) {
        Worker unmovedWorker = null;

        //note: this breaks with Bia and Medusa
        for (Worker w : workerList) {
            if (w != selectedWorker)
                unmovedWorker = w;
        }

        Square unmovedWorkerSquare = board.getSquare(unmovedWorker.getCoordinates());
        Square targetSquare = board.getSquare(target);

        if (unmovedWorkerSquare.isAdjacent(targetSquare) && targetSquare.isFree()) {
            targetSquare.removeTop();
            board.setChangedSquares(List.of(targetSquare));
            return true;
        } else return false;
    }

    @Override
    public AbstractTurn getTurn() {
        return new Ares.Turn();
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
                            actionsTaken.add(Action.BUILD);
                            availableActions.clear();
                            availableActions.add(Action.GODPOWER);
                            availableActions.add(Action.ENDTURN);
                            return true;
                        }
                        break;

                    case GODPOWER:
                        if(godpower(squareCoordinates)) {
                            actionsTaken.add(Action.GODPOWER);
                            availableActions.remove(Action.GODPOWER);
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
        }

        @Override
        public AbstractTurn copy() {
            return new Ares.Turn(this.availableActions, this.actionsTaken);
        }
    }

}
