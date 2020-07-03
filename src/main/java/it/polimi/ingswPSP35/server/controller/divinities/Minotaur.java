package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the implementation of the divinity Minotaur. Like Apollo, Minotaur can also move into spaces which contain
 * opponents' {@code Worker}s. <p>
 * This behaviour is achieved by overriding the {@code move} and {@code canMove} methods.
 *
 * @author Paolo Galli
 */
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
            //if the square of destination is not free, then move the worker occupying the destination square
            // to the next square in the same direction
            if (!destination.isFree()) {
                Worker opponent = (Worker) destination.getTop();
                destination.removeTop();
                Square nextInLine = getNextSquareInLine(origin, destination);
                //opponent can't ever be null because it's checked by canMove method
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
            board.setChangedSquares(List.of(origin, destination));

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
                    //check if destination is at most one block higher than the square of minotaur's worker
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

    //checks if the next square in the same direction of origin towards target is inbounds and free
    private boolean checkNextSquare(Square origin, Square target) {
        Square nextInLine = getNextSquareInLine(origin, target);
        if (nextInLine != null) {
            return nextInLine.isFree();
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
}