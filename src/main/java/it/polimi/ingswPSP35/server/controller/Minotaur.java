package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;


public class Minotaur extends Divinity {

    private final String Name = "Minotaur";

    @Override
    public void playTurn() {

    }


    @Override
    public boolean move(Square destination) {
        Square origin = board.getSquare(selectedWorker.getX(), selectedWorker.getY());
        if (canMove(selectedWorker, origin, destination)) {
            //if the square is not free, then use minotaur's godpower
            if (!destination.isFree()) {
                Worker opponent = (Worker) destination.getTop();
                destination.removeTop();
                Square nextInLine = getNextSquareInLine(origin, destination);
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
        }
        else return false;
    }

    @Override
    public boolean canMove(Worker worker, Square workerSquare, Square destination) {
        //if you can move normally, return true
        if (super.canMove(worker, workerSquare, destination)) {
            return true;
        }
        //check if you could move to "destination" if it was free
        return destination.isAdjacent(workerSquare)
                && destination.getHeight() <= workerSquare.getHeight() + 1
                //check if the next square on the same line is free
                && checkNextSquare(workerSquare, destination)
                //check if "destination" contains a worker of another player
                && (destination.getTop() instanceof Worker)
                && !((Worker) destination.getTop()).getPlayer().getDivinity().getName().equals(getName());
    }

    private boolean checkNextSquare(Square origin, Square target) {
        Square nextInLine = getNextSquareInLine(origin, target);
        if (nextInLine != null){
            return nextInLine.isFree();
        }
        else return false;
    }

    private Square getNextSquareInLine(Square origin, Square target) {
        int dx = target.getX() - origin.getX();
        int dy = target.getY() - origin.getY();
        //check if desired square is out of bounds
        if (((target.getX() + dx) < 5)
                && ((target.getY() + dy) < 5)
                && ((target.getX() + dx) >= 0)
                && ((target.getY() + dy) >= 0)) {
            return board.getSquare(target.getX()+dx, target.getY()+dy);
        }
        return null;
    }
}