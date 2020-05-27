package it.polimi.ingswPSP35.commons;

import it.polimi.ingswPSP35.server.model.ConcreteSquare;
import it.polimi.ingswPSP35.server.model.Dome;
import it.polimi.ingswPSP35.server.model.Worker;

public class ReducedSquare {
    private final int height;
    private final boolean hasDome;
    private final ReducedWorker worker;

    public ReducedSquare(ConcreteSquare square) {
        this.height = square.getHeight();
        this.hasDome = square.getTop() instanceof Dome;
        if (square.getTop() instanceof Worker)
            this.worker = new ReducedWorker((Worker) square.getTop());
        else this.worker = null;
    }
}

