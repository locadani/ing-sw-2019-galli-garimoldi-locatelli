package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.*;

public abstract class Divinity {
    private String Name;
    private boolean isLegalFor3Players;
    private boolean decorates = false;
    protected DivinityMediator divinityMediator;
    protected Worker selectedWorker;
    protected Board board;


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

    public void selectWorker(Worker w) {
        this.selectedWorker = w;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean move(Square destination) {
        Square origin = board.getSquare(selectedWorker.getX(), selectedWorker.getY());
        if (canMove(selectedWorker, destination)) {
            origin.removeTop();
            destination.insert(selectedWorker);
            selectedWorker.setX(destination.getX());
            selectedWorker.setY(destination.getY());
            checkWin(selectedWorker, origin);
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
        return destination.isFree()
                && destination.isAdjacent(origin)
                && destination.getHeight() <= origin.getHeight() + 1
                && divinityMediator.checkMove(worker, destination);
    }



    public boolean build(Square target) {
        if (canBuild(selectedWorker, target)) {
            if ((target.getHeight() < 4)) {
                target.insert(new Block());
            } else {
                target.insert(new Dome());
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean canBuild(Worker worker, Square target) {
        return target.isFree()
                && target.isAdjacent(worker.getSquare())
                && divinityMediator.checkBuild(worker, target);
    }

    public boolean checkWin (Worker worker, Square origin) {
        return (origin.getHeight() == 2)
                && (worker.getSquare().getHeight() == 3)
                && divinityMediator.checkWin(worker, origin);
    }

}