package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class Apollo extends Divinity {

    private final String name = "Apollo";

    @Override
    public String getName() {
        return name;
    }

    @Override
 public boolean move(Square destination) {
        Square origin = board.getSquare(selectedWorker.getX(), selectedWorker.getY());
        if (canMove(selectedWorker, origin, destination)) {
            //if the square is not free, switch opponent and selectedWorker's squares
            if (!destination.isFree()) {
                Worker opponent = (Worker) destination.getTop();
                destination.removeTop();
                origin.removeTop();
                destination.insert(selectedWorker);
                selectedWorker.setX(destination.getX());
                selectedWorker.setY(destination.getY());
                origin.insert(opponent);
                opponent.setX(origin.getX());
                opponent.setY(origin.getY());
            }
            else {
                //move as normal
                origin.removeTop();
                destination.insert(selectedWorker);
                selectedWorker.setX(destination.getX());
                selectedWorker.setY(destination.getY());
            }
            checkWin(selectedWorker, destination, origin);
            return true;
        } else return false;
    }

    @Override
    public boolean canMove(Worker worker, Square workerSquare, Square destination) {
        //if you can move normally, return true
        if (super.canMove(worker, workerSquare, destination)) {
            return true;
        }
        if (!destination.isFree()) {
            //check if "destination" contains a worker
            return (destination.getTop() instanceof Worker)
                    && destination.getHeight() <= workerSquare.getHeight() + 1
                    && destination.isAdjacent(workerSquare)
                    //check if "destination" contains a worker of another player
                    && !((Worker) destination.getTop()).getPlayer().getDivinity().getName().equals(getName());
        }
        else return false;
    }

    @Override
    public AbstractTurn getTurn() {
        return new Apollo.Turn();
    }


    public class Turn extends AbstractTurn {

        public Turn() {
            super();
        }

        private Turn(ArrayList<Action> availableActions, ArrayList<Action> actionsTaken) {
            super(availableActions, actionsTaken);
        }

        public boolean tryAction(Action action, Worker worker, Square square) {
            if (availableActions.contains(action)) {
                switch (action) {
                    case MOVE:
                        if (move(square)) {
                            selectWorker(worker);
                            actionsTaken.add(Action.MOVE);
                            availableActions.clear();
                            availableActions.add(Action.BUILD);
                            return true;
                        }
                    case BUILD:
                        if (build(square)) {
                            availableActions.clear();
                            actionsTaken.add(Action.BUILD);
                            availableActions.add(Action.ENDTURN);
                            return true;
                        }
                    case GODPOWER:
                        return false;
                    case ENDTURN:
                        reset();
                        return true;
                }
            }
            return false;
        }

        public void reset() {
            availableActions.clear();
            actionsTaken.clear();
            availableActions.add(Action.MOVE);
            selectWorker(null);
        }

        @Override
        public AbstractTurn copy() {
            return new Apollo.Turn(availableActions, actionsTaken);
        }
    }
}
