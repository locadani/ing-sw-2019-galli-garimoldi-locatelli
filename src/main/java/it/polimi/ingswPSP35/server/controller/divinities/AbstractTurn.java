package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.Exceptions.WinException;
import it.polimi.ingswPSP35.server.model.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTurn {
    protected ArrayList<Action> availableActions;
    protected ArrayList<Action> actionsTaken;
    public abstract AbstractTurn copy();
    public abstract boolean tryAction(Coordinates workerCoordinates, Action action, Coordinates squareCoordinates);

    public AbstractTurn()
    {
        actionsTaken = new ArrayList<>();
        availableActions = new ArrayList<>();
        reset();
    }

    protected AbstractTurn(ArrayList<Action> availableActions, ArrayList<Action> actionsTaken){
        this.availableActions = new ArrayList<Action>(availableActions);
        this.actionsTaken = new ArrayList<Action>(actionsTaken);
    }
    public ArrayList<Action> getActionsTaken() {
        return new ArrayList<Action>(actionsTaken);
    }

    public ArrayList<Action> getAvailableActions() {
        return new ArrayList<Action>(availableActions);
    }

    public void reset() {
        availableActions.clear();
        actionsTaken.clear();
        availableActions.add(Action.MOVE);
    }

}
