package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;


public class Minotaur extends Divinity {

    private String Name = "Minotaur";

    @Override
    public void playTurn() {

    }


    @Override
    public boolean move(Square destination) {
        Square origin = selectedWorker.getSquare();
        if (canMove(selectedWorker, destination)) {
            if (destination.isFree()){
                super.move(destination);
            }
            else {
                Worker opponent = (Worker) destination.getTop();
                destination.removeTop();
                Square nextInLine = getNextSquareInLine(origin, destination);
                nextInLine.insert(opponent);
                opponent.setSquare(nextInLine);

                destination.insert(selectedWorker);
                origin.removeTop();
                selectedWorker.setSquare(destination);
            }
            return true;
        }
        else return false;
    }

    @Override
    public boolean canMove(Worker worker, Square destination) {
        if (!super.canMove(worker, destination)) {
            //check if you could move to "destination" if it was free
            if (destination.isAdjacent(worker.getSquare())
                    && destination.getHeight() <= worker.getSquare().getHeight() + 1
                    //check if the next square on the same line is free
                    && checkNextSquare(worker.getSquare(), destination)) {
                //check if "destination" contains a worker of another player
                if ((destination.getTop() instanceof Worker)
                    && !((Worker) destination.getTop()).getPlayer().getDivinity().getName().equals(this.Name)) {
                    return true;
                }
            }
            else return false;
        }
        return true;
    }

    //if methods called
    private boolean checkNextSquare(Square origin, Square target) {
        Square nextInLine = getNextSquareInLine(origin, target);
        if (nextInLine != null){
            return nextInLine.isFree();
        }
        else return false;
    }

    private Square getNextSquareInLine(Square origin, Square target) {
        if (origin.isAdjacent(target)) {
            int dx = target.getX() - origin.getX();
            int dy = target.getY() - origin.getY();
            //check if desired square is out of bounds
            if (((target.getX() + dx) < 5)
                    && ((target.getY() + dy) < 5)
                    && ((target.getX() + dx) >= 0)
                    && ((target.getY() + dy) >= 0)) {
                return board.getSquare(target.getX()+dx, target.getY()+dy);
            }
        }
        return null;
    }
}