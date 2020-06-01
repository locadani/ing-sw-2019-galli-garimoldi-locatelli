package it.polimi.ingswPSP35.commons;

import it.polimi.ingswPSP35.server.model.Worker;

public class ReducedWorker {
    private int colour;

    public ReducedWorker(Worker worker) {
        this.colour = worker.getPlayer().getColour();
    }

    public int getColour() {
        return colour;
    }
}
