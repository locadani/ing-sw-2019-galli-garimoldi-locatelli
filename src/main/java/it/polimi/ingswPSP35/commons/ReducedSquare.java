package it.polimi.ingswPSP35.commons;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Dome;
import it.polimi.ingswPSP35.server.model.Worker;

public class ReducedSquare {
    private final int height;
    private final boolean hasDome;
    private final ReducedWorker worker;
    private Coordinates coordinates;


    public ReducedSquare(Square square) {
        this.height = square.getHeight();
        this.hasDome = square.getTop() instanceof Dome;
        this.coordinates = square.getCoordinates();
        if (square.getTop() instanceof Worker)
            this.worker = new ReducedWorker((Worker) square.getTop());
        else this.worker = null;
    }

    public ReducedSquare() {
        this.height = 0;
        this.hasDome = false;
        this.worker = null;
    }


    public int getHeight() {
        return height;
    }

    public boolean HasDome() {
        return hasDome;
    }

    public ReducedWorker getWorker() {
        return worker;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}

