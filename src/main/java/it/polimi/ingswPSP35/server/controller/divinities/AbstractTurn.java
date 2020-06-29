package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the sequence in which actions can be performed by a given {@code Divinity}. This is accomplished
 * by using two lists to create a behaviour similar to a finite state machine.<p>
 *
 * The initial state is set by the {@code reset} method, transitions between states can only be triggered by the
 * {@code tryAction} method, and are regulated by checking the elements of the list {@code ActionsTaken}.<p>
 *
 * Note that implementations of this class are either private inner classes of the corresponding {@code Divinity}
 * or {@code DefaultTurn}<p>
 *
 * @author Paolo Galli
 * @see DefaultTurn
 */
public abstract class AbstractTurn {
    /**
     * List of all actions that can be performed from the current state
     */
    protected List<Action> availableActions;
    /**
     * List of all actions that have been taken in the current turn
     */
    protected List<Action> actionsTaken;

    /**
     * Returns a new instance of this AbstractTurn in its current state. Note that calling {@code tryAction} on the new
     * instance might alter the state of the game if the corresponding {@code Divinity} is not handled properly
     * @return a new instance of this AbstractTurn in its current state
     */
    public abstract AbstractTurn copy();

    /**
     * Attempts to perform an action by calling methods of this {@code AbstractTurn}'s corresponding {@code Divinity}.<p>
     *
     * If the action is successful, the state is updated and the methods returns true.
     *
     * @param workerCoordinates {@code Coordinates} of the {@code Worker} attempting the Action
     * @param action {@code Action} to be attempted
     * @param squareCoordinates {@code Coordinates} of the square to attempt the action on
     * @return true if the action is successful
     */
    public abstract boolean tryAction(Coordinates workerCoordinates, Action action, Coordinates squareCoordinates);

    /**
     * Default constructor
     */
    public AbstractTurn()
    {
        actionsTaken = new ArrayList<>();
        availableActions = new ArrayList<>();
        reset();
    }

    /**
     * Constructor with set state
     * @param availableActions List of actions that can be performed in the current state
     * @param actionsTaken List of actions that have already been performed
     */
    protected AbstractTurn(List<Action> availableActions, List<Action> actionsTaken){
        this.availableActions = new ArrayList<Action>(availableActions);
        this.actionsTaken = new ArrayList<Action>(actionsTaken);
    }

    public List<Action> getActionsTaken() {
        return new ArrayList<Action>(actionsTaken);
    }

    public List<Action> getAvailableActions() {
        return new ArrayList<Action>(availableActions);
    }

    /**
     * Method called to reset the class to the initial state. <p>
     * This method is only called by the constructor and after this turn has concluded normally.
     */
    public void reset() {
        availableActions.clear();
        actionsTaken.clear();
        availableActions.add(Action.MOVE);
    }

}
