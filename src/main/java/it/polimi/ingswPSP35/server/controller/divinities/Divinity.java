package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.model.*;

import java.util.List;

public abstract class Divinity {
    private boolean isLegalFor3Players;
    protected DivinityMediator divinityMediator;
    protected Worker selectedWorker;
    protected Board board;



    private void notify(List<Square> changedSquares)
    {

    }
    public abstract String getName();

    public boolean isLegalFor3Players() {
        return isLegalFor3Players;
    }

    public void setDivinityMediator(DivinityMediator divinityMediator) {
        this.divinityMediator = divinityMediator;
    }

    public void selectWorker(Worker w) {
        this.selectedWorker = w;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    /**Method called during setup to allow each divinity to decorate the DivinityMediator
     *
     * @param d mediator to decorate
     * @return a decorated version of "d" if the divinity has decorated it, otherwise the same mediator that was passed as a parameter
     */
    public DivinityMediator decorate(DivinityMediator d) {
        return d;
    }

    /**Attempts to move "selectedWorker" to Square "destination"
     * @param destination the Square one wishes to move to
     * @return true if the move action attempt was successful
     */
    public boolean move(Square destination) {
        Square origin = board.getSquare(selectedWorker.getR(), selectedWorker.getC());
        if (canMove(selectedWorker, origin, destination)) {
            origin.removeTop();
            destination.insert(selectedWorker);
            selectedWorker.setR(destination.getR());
            selectedWorker.setC(destination.getC());
            checkWin(selectedWorker, destination, origin);
            return true;
        } else {
            return false;
        }
    }

    /**verifies if it is possible to move "worker" from its Square "workerSquare" to Square "destination"
     *
     * @author Paolo Galli
     * @param worker Worker to be moved
     * @param workerSquare the Square "worker" is on
     * @param destination Square of destination
     * @return true if the move action is allowed
     */
    public boolean canMove(Worker worker, Square workerSquare, Square destination) {
        return destination.isFree()
                && destination.isAdjacent(workerSquare)
                && destination.getHeight() <= workerSquare.getHeight() + 1
                && divinityMediator.checkMove(worker, workerSquare, destination);
    }

    /**Attempts to build from "selectedWorker" to Square "target"
     * @param target the Square one wishes to build on
     * @return true if the build action attempt was successful
     */
    public boolean build(Square target) {
        Square workerSquare = board.getSquare(selectedWorker.getR(), selectedWorker.getC());
        if (canBuild(selectedWorker, workerSquare, target)) {
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

    /**verifies if it is possible to build with "worker" from its Square "workerSquare" on Square "target"
     *
     * @author Paolo Galli
     * @param worker Worker to be moved
     * @param workerSquare the Square "worker" is on
     * @param target the Square one wishes to build on
     * @return true if the move action is allowed
     */
    public boolean canBuild(Worker worker, Square workerSquare, Square target) {
        return target.isFree()
                && target.isAdjacent(workerSquare)
                && divinityMediator.checkBuild(worker,workerSquare, target);
    }


    /**verifies whether "worker" has won by performing an action from Square "origin" to Square "current"
     *
     * @param worker Worker that has performed and Action
     * @param current Square that "worker" is currently occupying
     * @param origin Square that "worker was on before performing an action
     * @return ???
     */
    //TODO decide how to handle victory
    public boolean checkWin (Worker worker, Square current, Square origin) {
        return (origin.getHeight() == 2)
                && (current.getHeight() == 3)
                && divinityMediator.checkWin(worker, current, origin);
    }

    public abstract AbstractTurn getTurn();
}