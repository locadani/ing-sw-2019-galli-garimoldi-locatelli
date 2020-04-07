package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public abstract class Divinity {
    private String Name;
    private boolean isLegalFor3Players;
    protected DivinityMediator divinityMediator;
    protected Worker selectedWorker;


    public String getName() {
        return Name;
    }

    public boolean isLegalFor3Players() {
        return isLegalFor3Players;
    }

    public abstract void playTurn();

    public void setDivinityMediator(DivinityMediator divinityMediator) {
        this.divinityMediator = divinityMediator;
    }

    public void selectWorker (Worker w) {
        this.selectedWorker = w;
    }


    public boolean move(Square destination) {
        if (canMove(selectedWorker, destination)) {
            selectedWorker.getSquare().removeTop();
            destination.insert(selectedWorker);
            selectedWorker.setSquare(destination);
            return true;
        } else {
            return false;
        }
    }

    /**
     *verifies if it is possible to move a Worker to a specified Square
     *
     * @author Paolo Galli
     * @param worker Worker to be moved
     * @param destination Square of destination
     * @return a boolean that expresses whether the move is allowed
     */
    public boolean canMove(Worker worker, Square destination) {
        Square origin = worker.getSquare();
        return (destination.isFree()
                && destination.isAdjacent(origin)
                && destination.getHeight() <= origin.getHeight() + 1
                && checkMove(worker, destination));
    }

    private boolean checkMove(Worker worker, Square destination) {
        return divinityMediator.checkMove(worker, destination);
    }

}