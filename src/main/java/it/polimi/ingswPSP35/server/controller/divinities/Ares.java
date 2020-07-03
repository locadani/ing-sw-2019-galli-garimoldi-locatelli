package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the implementation of the divinity Ares. It's the only {@code Divinity} that retains references to
 * its workers at setup time in order to find the unmoved {@code Worker}.
 *
 * @author Paolo Galli
 */
public class Ares extends Divinity {
    private static String name = "Ares";
    private List<Worker> workerList = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc} <p>
     * Because Ares' god power applies to his unmoved {@code Worker}, this method allows him to retain reference to
     * both of his workers, unlike other Divinities. <p>
     * This might cause memory leaks and similar issues if divinities that remove workers (Bia and Medusa) are implemented.
     *
     * @param worker worker to be placed
     * @param coordinates {@code Coordinates} of the Square one wishes to place {@code worker} on
     * @return true if {@code worker} has been placed successfully
     */
    @Override
    public boolean placeWorker(Worker worker, Coordinates coordinates) {
        if (super.placeWorker(worker, coordinates)) {
            workerList.add(worker);
            return true;
        } else return false;
    }

    /**
     * Attempts to use Ares' god power on the {@code Square} identified by {@code Coordinates}. <p>
     * Namely, if {@code target} corresponds to a {@code Square} that is adjacent to Ares' unmoved worker, doesn't contain
     * a {@code Worker} or {@code Dome}, and has at least one {@code Block}, a {@code} block is removed from said {@code Square}
     * and the method return true.
     *
     * @param target {@code Coordinates} of the {@code Square} one wishes to apply Ares' god power
     * @return true if Ares' god power can be applied
     */
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
        public AbstractTurn copy() {
            return new Ares.Turn(this.availableActions, this.actionsTaken);
        }
    }

}
