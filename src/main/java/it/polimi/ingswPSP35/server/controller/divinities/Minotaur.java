package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;


public class Minotaur extends Divinity {

    private final String name = "Minotaur";

    public String getName() {
        return this.name;
    }

    @Override
    public boolean move(Coordinates destinationCoordinates) {
        List<Square> changedSquares = new ArrayList<>();
        Square destination = board.getSquare(destinationCoordinates);
        Square origin = board.getSquare(selectedWorker.getR(), selectedWorker.getC());
        if (canMove(selectedWorker, origin, destination)) {
            //if the square is not free, then move the worker occupying the square to the next square in the same direction
            if (!destination.isFree()) {
                Worker opponent = (Worker) destination.getTop();
                destination.removeTop();
                Square nextInLine = getNextSquareInLine(origin, destination);
                //opponent can't be null because it's checked by canMove method
                nextInLine.insert(opponent);
                opponent.setCoordinates(nextInLine.getCoordinates());
                changedSquares.add(nextInLine);
            }
            //move as normal
            origin.removeTop();
            destination.insert(selectedWorker);
            selectedWorker.setCoordinates(destination.getCoordinates());

            changedSquares.add(origin);
            changedSquares.add(destination);
            board.setChangedSquares(changedSquares);

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
                    && checkNextSquare(workerSquare, destination)
                    && divinityMediator.checkMove(worker, workerSquare, destination);
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
}