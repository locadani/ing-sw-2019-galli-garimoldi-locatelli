package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.Winner;
import it.polimi.ingswPSP35.server.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides default behaviour for all actions a divinity cards share, but doesn't specify the order in
 * which they can be taken. This includes moving, building and checking for victory. <p>
 *
 * This class has a reference to two of the main logical components of the game: {@code Board} and {@code divinityMediator},
 * both of which are shared between all divinities engaged in the same match. Specifically, these references are
 * passed during the setup phase by calling two setter methods. <p>
 *
 * The order in which actions can be taken is decided by the {@code AbstractTurn} implementation returned by the
 * abstract method {@code getTurn()}.<p>
 *
 * This class also has a referenced to an instance of {@code Winner} class shared by all the divinities
 * involved in the same match. This class is used to handle win conditions and the end of the game.<p>
 *
 * @author Paolo Galli
 * @see Board
 * @see AbstractTurn
 * @see DivinityMediator
 */
public abstract class Divinity {
    /**
     * {@code DivinityMediator} instance shared by all divinities in the same match.
     */
    protected DivinityMediator divinityMediator;
    /**
     * {@code Board} instance shared by all divinities in the same match.
     */
    protected Board board;
    /**
     * {@code Worker} selected at the beginning of each turn to attempt actions.
     */
    protected Worker selectedWorker;
    /**
     * {@code Winner} instance shared by all divinities in the same match to handle victory.
     */
    protected Winner winner;

    /**
     * Setter for {@code Winner} parameter. Only called at during setup once per game.
     *
     * @param winner {@code Winner} instance for the current game
     */
    public void setWinner(Winner winner) {
        this.winner = winner;
    }

    /**
     * Method used to identify divinities. It returns this {@code Divinity}'s name as it is spelled in the official
     * rulebook (in english), starting with a capital letter. This also coincides with the class name.
     *
     * @return {@code Divinity}'s name
     */
    public abstract String getName();

    /**
     * Setter for {@code DivinityMediator} parameter. Only called during setup once per game after the mediator has been
     * decorated by all Divinities.
     *
     * @param divinityMediator {@code DivinityMediator} to be set
     */
    public void setDivinityMediator(DivinityMediator divinityMediator) {
        this.divinityMediator = divinityMediator;
    }

    /**
     * Method called at the start of each turn to determine which {@code Worker} will take actions
     *
     * @param workerCoordinates coordinates of the chosen {@code Worker}
     * @return true if the square corresponding to {@code workerCoordinates} contains a {@code Worker} of this {@code Divinity}
     * @author Paolo Galli
     */
    public boolean selectWorker(Coordinates workerCoordinates) {
        Piece top = board.getSquare(workerCoordinates).getTop();
        //check if worker is present and owned by this divinity
        if (top instanceof Worker
                && ((Worker) top).getPlayer().getDivinity().getName().equals(this.getName())) {
            this.selectedWorker = (Worker) board.getSquare(workerCoordinates).getTop();
            return true;
        } else return false;
    }

    /**
     * Getter for {@code selectedWorker} field. It's called only by {@code DefeatChecker} to be able to restore this
     * {@code Divinity}'s state.
     *
     * @return {@code selectedWorker} field
     * @see it.polimi.ingswPSP35.server.controller.DefeatChecker
     */
    public Worker getSelectedWorker() {
        return selectedWorker;
    }

    /**
     * Setter for {@code board} field. It's called only by {@code DefeatChecker} to be able to restore this
     * {@code Divinity}'s state.
     *
     * @param board {@code Board} instance shared by all {@code Divinity} instances in the same game
     */
    public void setBoard(Board board) {
        this.board = board;
    }


    /**
     * Method called during setup to allow each divinity to decorate a shared DivinityMediator.
     *
     * @param toDecorate {@code DivinityMediator} to decorate
     * @return a decorated version of {@code toDecorate} if the divinity has decorated it, {@code toDecorate} otherwise
     * @author Paolo Galli
     */
    public DivinityMediator decorate(DivinityMediator toDecorate) {
        return toDecorate;
    }

    /**
     * Attempts to move {@code selectedWorker} to Square {@code destination}. If the attempt is successful, the board
     * is notified of what squares have been affected by the move action.
     *
     * @param destinationCoordinates the Square one wishes to move to
     * @return true if the move action attempt was successful
     */
    public boolean move(Coordinates destinationCoordinates) {
        Square origin = board.getSquare(selectedWorker.getCoordinates());
        Square destination = board.getSquare(destinationCoordinates);
        if (canMove(selectedWorker, origin, destination)) {
            origin.removeTop();
            destination.insert(selectedWorker);
            selectedWorker.setCoordinates(destination.getCoordinates());

            board.setChangedSquares(List.of(origin, destination));

            checkWin(selectedWorker, destination, origin);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verifies if it is possible to move {@code worker} from its current Square {@code workerSquare} to Square
     * {@code destination}.
     *
     * @param worker       Worker to be moved
     * @param workerSquare the Square {@code worker} is on
     * @param destination  Square of destination
     * @return true if the move action is allowed, false otherwise
     * @author Paolo Galli
     */
    protected boolean canMove(Worker worker, Square workerSquare, Square destination) {
        return destination.isFree()
                && destination.isAdjacent(workerSquare)
                && destination.getHeight() <= workerSquare.getHeight() + 1
                && divinityMediator.checkMove(worker, workerSquare, destination);
    }

    /**
     * Attempts to build from {@code selectedWorker} to Square {@code target}. If the attempt is successful, the board
     * is notified of what squares have been affected by the build action.
     *
     * @param targetCoordinates the Square one wishes to build on
     * @return true if the build action attempt was successful
     * @author Paolo Galli
     */
    public boolean build(Coordinates targetCoordinates) {
        Square workerSquare = board.getSquare(selectedWorker.getCoordinates());
        Square target = board.getSquare(targetCoordinates);
        if (canBuild(selectedWorker, workerSquare, target)) {
            if ((target.getHeight() < 3)) {
                target.insert(new Block());
            } else {
                target.insert(new Dome());
            }

            board.setChangedSquares(List.of(target));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verifies if it is possible to build {@code worker} from its current Square {@code workerSquare} to Square
     * {@code target}.
     *
     * @param worker       Worker to be moved
     * @param workerSquare the Square "worker" is on
     * @param target       the Square one wishes to build on
     * @return true if the move action is allowed
     * @author Paolo Galli
     */
    protected boolean canBuild(Worker worker, Square workerSquare, Square target) {
        return target.isFree()
                && target.isAdjacent(workerSquare)
                && divinityMediator.checkBuild(worker, workerSquare, target);
    }


    /**
     * Verifies whether {@code worker} has won by performing an action from Square {@code origin} to Square
     * {@code current}. If that's the case, this instance will call a method of the shared {@code Winner} class to
     * declare itself the winner.
     *
     * @param worker  Worker that has performed and Action
     * @param current Square that contains {@code worker}
     * @param origin  Square that {@code worker} was on before performing an action
     */
    protected void checkWin(Worker worker, Square current, Square origin) {
        if ((origin.getHeight() == 2)
                && (current.getHeight() == 3)
                && divinityMediator.checkWin(worker, current, origin))
            winner.setWinner(this);
    }

    /**
     * Returns an implementation of {@code AbstractTurn} specific to the concrete Divinity that implements this method
     *
     * @return an implementation of {@code AbstractTurn}
     */
    public AbstractTurn getTurn() {
        return new DefaultTurn(this);
    }

    /**
     * Method called during setup to check whether {@code worker} can be placed on a given Square (identified
     * by {@code coordinates}).
     *
     * @param worker      worker to be placed
     * @param coordinates {@code Coordinates} of the Square one wishes to place {@code worker} on
     * @return true if {@code worker} has been placed on the Square identified by {@code coordinates}, false otherwise
     */
    public boolean placeWorker(Worker worker, Coordinates coordinates) {
        Square chosenSquare = board.getSquare(coordinates);
        if (chosenSquare.isFree()) {
            board.getSquare(coordinates).insert(worker);
            worker.setCoordinates(coordinates);

            board.setChangedSquares(List.of(chosenSquare));
            return true;
        }
        return false;
    }
}