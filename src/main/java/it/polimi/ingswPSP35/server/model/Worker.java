package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.ReducedWorker;

public class Worker implements Piece {
    private Coordinates coordinates;

    private final Player player;

    public Worker(Coordinates coordinates, Player player){
        this.coordinates = coordinates;
        this.player = player;
    }

    public Worker(Player player)
    {
        this.player = player;
    }

    public Coordinates getCoordinates(){ return coordinates;}

    public int getR() {
        return coordinates.getR();
    }

    public int getC() {
        return coordinates.getC();
    }

    public void setCoordinates(Coordinates coordinates) {this.coordinates = coordinates;}

    public Player getPlayer() {
        return player;
    }

    public Worker copy() {
        return new Worker(this.coordinates, this.getPlayer());
    }

    public ReducedWorker reduce() {
        return new ReducedWorker(this);
    }
}
