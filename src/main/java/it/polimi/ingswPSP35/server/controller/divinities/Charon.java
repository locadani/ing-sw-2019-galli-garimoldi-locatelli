package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;

/**
 * This class is the implementation of the divinity Charon. At the start of its turn, Charon can move an adjacent opponent's
 * to the {@code Square} directly on the other side of its {@code Worker}, if that space is unoccupied. <p>
 * This behaviour is achieved with a custom implementation of {@code AbstractTurn} and a special method.
 *
 * @author Paolo Galli
 */
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

    /**
     * Assuming {@code target} contains an opponents {@code Worker} and is adjacent to {@code selectedWorker}, this method
     * attempts to move the {@code Worker} on {@code target} to the {@code Square} directly on the other side of
     * {@code selectedWorker}. If the attempt is successful, the board is notified of the changes.
     *
     * @param target position of the {@code Square} containing the {@code Worker}
     * @return true if the attempt is successful
     */
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

    //returns the next square in the same direction of origin towards target
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
