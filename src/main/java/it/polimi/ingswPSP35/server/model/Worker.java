package it.polimi.ingswPSP35.server.model;

public class Worker extends Piece {
    private int x;
    private int y;
    private final Player player;

    public Worker(int x, int y, Player player){
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Player getPlayer() {
        return player;
    }
 
    public Worker copy() {
        Worker copy = new Worker(this.x, this.y, this.getPlayer());
        return copy;
    }


}
