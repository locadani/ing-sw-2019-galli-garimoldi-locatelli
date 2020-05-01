package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;


public class Minotaur extends Divinity {

    private final String name = "Minotaur";

    public String getName() {
        return this.name;
    }

    @Override
    public boolean move(Square destination) {
        Square origin = board.getSquare(selectedWorker.getX(), selectedWorker.getY());
        if (canMove(selectedWorker, origin, destination)) {
            //if the square is not free, then move the worker occupying the square to the next square in the same direction
            if (!destination.isFree()) {
                Worker opponent = (Worker) destination.getTop();
                destination.removeTop();
                Square nextInLine = getNextSquareInLine(origin, destination);
                //opponent can't be null because it's checked by canMove method
                nextInLine.insert(opponent);
                opponent.setX(nextInLine.getX());
                opponent.setY(nextInLine.getY());
            }
            //move as normal
            origin.removeTop();
            destination.insert(selectedWorker);
            selectedWorker.setX(destination.getX());
            selectedWorker.setY(destination.getY());
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
                    && !((Worker) destination.getTop()).getPlayer().getDivinity().getName().equals(getName())
                    //check if the next square on the same line is free
                    && checkNextSquare(workerSquare, destination);
        }
        else return false;
    }

    private boolean checkNextSquare(Square origin, Square target) {
        Square nextInLine = getNextSquareInLine(origin, target);
        if (nextInLine != null) {
            return nextInLine.isFree();
        } else return false;
    }

    private Square getNextSquareInLine(Square origin, Square target) {
        int dx = target.getX() - origin.getX();
        int dy = target.getY() - origin.getY();
        //check if desired square is out of bounds
        if (((target.getX() + dx) < 5)
                && ((target.getY() + dy) < 5)
                && ((target.getX() + dx) >= 0)
                && ((target.getY() + dy) >= 0)) {
            return board.getSquare(target.getX() + dx, target.getY() + dy);
        }
        return null;
    }

    @Override
    public AbstractTurn getTurn() {
        return new Minotaur.Turn();
    }

    private class Turn extends AbstractTurn {
        private List<Action> availableActions;
        private List<Action> actionsTaken;

        public Turn() {
            reset();
        }

        private Turn(List<Action> availableActions, List<Action> actionsTaken) {
            this.availableActions = List.copyOf(availableActions);
            this.actionsTaken = List.copyOf(actionsTaken);
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

        public List<Action> getActionsTaken() {
            return List.copyOf(actionsTaken);
        }

        public List<Action> getAvailableActions() {
            return List.copyOf(availableActions);
        }

        @Override
        public AbstractTurn copy() {
            return new Minotaur.Turn(this.availableActions, this.actionsTaken);
        }
    }
}