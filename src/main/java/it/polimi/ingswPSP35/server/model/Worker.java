package it.polimi.ingswPSP35.server.model;

public class Worker implements Piece {
    private int r;
    private int c;

    private final Player player;

    public Worker(int r, int c, Player player){
        this.r = r;
        this.c = c;
        this.player = player;
    }

    public Worker(Player player)
    {
        this.player = player;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String getName() {
        return "WORKER";
    }

    public Worker copy() {
        Worker copy = new Worker(this.r, this.c, this.getPlayer());
        return copy;
    }


}
