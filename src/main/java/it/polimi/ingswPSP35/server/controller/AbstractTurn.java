package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;

public abstract class AbstractTurn {
    public abstract AbstractTurn copy();
    public abstract List<Action> getAvailableActions();
    public abstract List<Action> getActionsTaken();
    public abstract boolean tryAction(Action action, Worker worker, Square square);
}
