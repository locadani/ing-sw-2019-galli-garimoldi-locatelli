package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;


/**
 * Class that handles all god powers that influence other players' turns. It is initialized during the setup phased and
 * decorated if divinities with such god powers have been selected.<p>
 * If there are no divinities that influence other players' turns in any given game, this class serves no purpose, as
 * it's methods will always return true.
 *
 * @author Paolo Galli
 * @see DivinityMediatorDecorator
 */
//TODO add "copy" method if Chronus is implemented, to be used by DefeatChecker
public class DivinityMediator {

    /**
     * Method called before any divinity performs a move action to check if the move is allowed by all divinities.
     *
     * @author Paolo Galli
     * @param workerToBeMoved Worker the caller is attempting to move
     * @param workerSquare Square containing the worker attempting to move
     * @param destination Square the worker is attempting to move to
     * @return true if the move is allowed, false otherwise
     * */
    public boolean checkMove(Worker workerToBeMoved, Square workerSquare, Square destination) {
        return true;
    }

    /**
     * Method called before any divinity performs a build action to check if the action is allowed by all divinities.
     * This method is currently unnecessary, but will be needed if Limus and/or Aphrodite are implemented.
     *
     * @author Paolo Galli
     * @param worker Worker the caller is attempting to build with
     * @param workerSquare Square containing the worker attempting to build
     * @param target Square the worker is attempting to build on
     * @return true if the build action is allowed, false otherwise
     */
    public boolean checkBuild(Worker worker,Square workerSquare, Square target) {
        return true;
    }

    /**
     * Method called before any divinity declares itself the winner, to check if the action is allowed by all divinities.
     * Because all divinities with additional win conditions either move (Pan) or are not affected by other divinities
     * (Chronus), this method will only (and always) be called after a move action; hence the need for the "origin"
     * parameter, as the move has already happened and this class can't and shouldn't be able to obtain the previous
     * state of the game (in this case, the worker's position before calling this method).
     * This method is currently unnecessary, but will be needed if Hera and/or Dionysus are implemented.
     *
     * @author Paolo Galli
     * @param worker Worker that has potentially won the game
     * @param workerSquare Square containing the worker that has potentially won the game
     * @param origin Square that contained the worker before moving and calling this method
     * @return true if the worker has won, false otherwise
     */
    //note: most win conditions are checked after moving
    public boolean checkWin(Worker worker, Square workerSquare, Square origin) {
        return true;
    }
}
