package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTurn {
    protected List<Action> availableActions;
    protected List<Action> actionsTaken;
    public abstract AbstractTurn copy();
    public abstract boolean tryAction(Action action, Worker worker, Square square);
    public abstract void reset();
    public AbstractTurn()
    {
        actionsTaken = new ArrayList<>();
        availableActions = new ArrayList<>();
        reset();
    }

    protected AbstractTurn(List<Action> availableActions, List<Action> actionsTaken){
        this.availableActions = List.copyOf(availableActions);
        this.actionsTaken = List.copyOf(actionsTaken);
    }
    public List<Action> getActionsTaken() {
        return List.copyOf(actionsTaken);
    }

    public List<Action> getAvailableActions() {
        return List.copyOf(availableActions);
    }
}
