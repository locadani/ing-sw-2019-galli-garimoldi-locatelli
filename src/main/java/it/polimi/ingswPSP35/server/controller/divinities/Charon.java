package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class Charon extends Divinity {
    private static String name = "Charon";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbstractTurn getTurn() {
        return new Charon.Turn();
    }

    public boolean godpower(Coordinates target) {
        Square workerSquare = board.getSquare(selectedWorker.getCoordinates());
        Square targetSquare = board.getSquare(target);
        Square nextInLine = getNextSquareInLine(targetSquare, workerSquare);

        if (workerSquare.isAdjacent(targetSquare) && nextInLine != null && nextInLine.isFree()) {
            Worker opponent = (Worker) targetSquare.getTop();
            targetSquare.removeTop();
            nextInLine.insert(opponent);

            board.setChangedSquares(List.of(targetSquare, nextInLine));

            return true;
        } else return false;
    }

    private Square getNextSquareInLine(Square origin, Square target) {
        int dr = target.getR() - origin.getR();
        int dc = target.getC() - origin.getC();
        //check if desired square is out of bounds
        if (((target.getR() + dr) < 5)
                && ((target.getC() + dc) < 5)
                && ((target.getR() + dr) >= 0)
                && ((target.getC() + dc) >= 0)) {
            return board.getSquare(target.getR() + dr, target.getC() + dc);
        }
        return null;
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
                         if (build(squareCoordinates)) {
                            actionsTaken.add(Action.BUILD);
                            availableActions.clear();
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
            availableActions.add(Action.GODPOWER);
        }

        @Override
        public AbstractTurn copy() {
            return new Charon.Turn(this.availableActions, this.actionsTaken);
        }
    }
}
