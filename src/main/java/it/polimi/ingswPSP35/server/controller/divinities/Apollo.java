package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
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
    public boolean move(Coordinates destinationCoordinates) {
        List<Square> changedSquares = new ArrayList<>();
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
                    && divinityMediator.checkMove(worker, workerSquare, destination);
        } else return false;
    }

    @Override
    public AbstractTurn getTurn() {
        return new DefaultTurn(this);
    }
}
