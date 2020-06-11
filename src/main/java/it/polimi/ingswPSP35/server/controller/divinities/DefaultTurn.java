package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;

import java.util.ArrayList;

public class DefaultTurn extends AbstractTurn {
    private final Divinity divinity;

    public DefaultTurn(Divinity divinity) {
        super();
        this.divinity = divinity;
    }

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

    private DefaultTurn(ArrayList<Action> availableActions, ArrayList<Action> actionsTaken, Divinity divinity) {
        super(availableActions, actionsTaken);
        this.divinity = divinity;
    }
}
