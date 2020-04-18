package it.polimi.ingswPSP35.server.model;

public class Worker extends Piece {
    private Square square;
    private final Player player;

    public Worker(Square square, Player player){
        this.square = square;
        this.player = player;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Player getPlayer() {
        return player;
    }

    public Worker copy() {
        Worker copy = new Worker(null, this.getPlayer());
        return copy;
    }
}
