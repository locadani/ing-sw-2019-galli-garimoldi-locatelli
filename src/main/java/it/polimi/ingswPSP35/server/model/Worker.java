package it.polimi.ingswPSP35.server.model;

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

    @Override
    public String getName() {
        return "WORKER";
    }

    public Worker copy() {
        Worker copy = new Worker(this.coordinates, this.getPlayer());
        return copy;
    }

}
