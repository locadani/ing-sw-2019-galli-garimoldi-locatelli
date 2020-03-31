package it.polimi.ingswPSP35.server.model;

import java.util.ArrayList;

public class Square {
    private int x;
    private int y;
    private int height = 0;
    private ArrayList<Piece> pieceList;

    private static Block block;
    private static Dome dome;
    private static Worker worker;

    Square(int x, int y){
        this.x = x;
        this.y = y;
        this.pieceList = pieceList;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Piece> getPieceList() {
        return pieceList;
    }

    public Piece getTop() {
        return pieceList.get(pieceList.size()-1);
    }

    public boolean isFree() {
        return getTop().getClass() == block.getClass() || getTop() == null;
    }

    public void insert(Piece p) {}

    public  void remove(Piece p) {}
}
