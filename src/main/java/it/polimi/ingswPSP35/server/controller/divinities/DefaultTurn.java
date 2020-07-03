package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;

import java.util.List;

/**
 * Implementation of {@code AbstractTurn} used by all Divinities that have a turn strictly composed only of a move action
 * followed by a build action.
 *
 * @author Paolo Galli
 *
 * @see Apollo
 * @see Athena
 * @see Hera
 * @see Limus
 * @see Minotaur
 * @see Pan
 */
public class DefaultTurn extends AbstractTurn {
    private final Divinity divinity;

    /**
     * Standard constructor: creates a new instance of {@code DefaultTurn} in its initial state
     *
     * @param divinity {@code Divinity} that owns this {@code AbstractTurn}
     */
    public DefaultTurn(Divinity divinity) {
        super();
        this.divinity = divinity;
    }

    /**
     * This constructor creates a new instance of {@code DefaultTurn} preserving its state, assuming it is passed
     * a legal state ({@code availableActions} and {@code actionsTaken}) for this class.
     *
     * @param availableActions List of actions that can be performed in the current state
     * @param actionsTaken List of actions that have already been performed
     * @param divinity {@code Divinity} that owns this {@code AbstractTurn}
     */
    private DefaultTurn(List<Action> availableActions, List<Action> actionsTaken, Divinity divinity) {
        super(availableActions, actionsTaken);
        this.divinity = divinity;
    }

    @Override
    public boolean tryAction(Coordinates workerCoordinates, Action action, Coordinates squareCoordinates) {

        if (actionsTaken.isEmpty())
            if (!divinity.selectWorker(workerCoordinates))
                return false;

        if (availableActions.contains(action)) {
            switch (action) {
                case MOVE:
                    if (divinity.move(squareCoordinates)) {
                        actionsTaken.add(Action.MOVE);
                        availableActions.clear();
                        availableActions.add(Action.BUILD);
                        return true;
                    }
                    break;

                case BUILD:
                    if (divinity.build(squareCoordinates)) {
                        availableActions.clear();
                        actionsTaken.add(Action.BUILD);
                        availableActions.add(Action.ENDTURN);
                        return true;
                    }
                    break;

                case ENDTURN:
                    reset();
                    return true;
            }
        }
        return false;
    }


    @Override
    public AbstractTurn copy() {
        return new DefaultTurn(this.availableActions, this.actionsTaken, this.divinity);
    }
}
