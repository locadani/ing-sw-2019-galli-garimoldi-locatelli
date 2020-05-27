package it.polimi.ingswPSP35.commons;

import it.polimi.ingswPSP35.server.model.Worker;

public class ReducedWorker {
    String username;

    public ReducedWorker(Worker worker) {
        this.username = worker.getName();
    }
}
