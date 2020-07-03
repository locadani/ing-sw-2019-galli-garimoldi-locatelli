package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;

/**
 * This class is the implementation of the divinity Apollo. Like Minotaur, Apollo can also move into spaces which contain
 * opponents' {@code Worker}s. <p>
 * This behaviour is achieved by overriding the {@code move} and {@code canMove} methods.

 * @author Paolo Galli
 */
public class Apollo extends Divinity {

    private final String name = "Apollo";

    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc} <p>
     * Because Apollo can also move into a {@code Square} occupied by an opponents worker, this method provides this
     * functionality in addition to the default behaviour implemented by the superclass {@code Divinity}
     * @param destinationCoordinates the Square one wishes to move to
     * @return true if the move was performed successfully
     */
    @Override
    public boolean move(Coordinates destinationCoordinates) {
        Square origin = board.getSquare(selectedWorker.getCoordinates());
        Square destination = board.getSquare(destinationCoordinates);
        if (canMove(selectedWorker, origin, destination)) {
            //if the square is not free, switch opponent and selectedWorker's squares
            if (!destination.isFree()) {
                Worker opponent = (Worker) destination.getTop();
                destination.removeTop();
                origin.removeTop();
                destination.insert(selectedWorker);
                selectedWorker.setCoordinates(destination.getCoordinates());
                origin.insert(opponent);
                opponent.setCoordinates(origin.getCoordinates());
            } else {
                //move as normal
                origin.removeTop();
                destination.insert(selectedWorker);
                selectedWorker.setCoordinates(destination.getCoordinates());
            }

            board.setChangedSquares(List.of(origin, destination));

            checkWin(selectedWorker, destination, origin);
            return true;
        } else return false;
    }

    /**
     * {@inheritDoc}
     * Because Apollo can also move into a {@code Square} occupied by an opponents worker, this method returns true
     * even if {@code destination} contains a {@code Worker} of another {@code Divinity}
     * @param worker Worker to be moved
     * @param workerSquare the Square {@code worker} is on
     * @param destination Square of destination
     * @return
     */
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
                    && divinityMediator.checkMove(worker, workerSquare, destination);
        } else return false;
    }
}
