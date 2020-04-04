package it.polimi.ingswPSP35.server.model;

import java.util.Stack;

import static java.lang.Math.abs;

public class Square {
    private int x;
    private int y;
    private int height = 0;
    private Stack<Piece> pieceStack;

    private static Block block;
    private static Dome dome;
    private static Worker worker;

    Square(int x, int y){
        this.x = x;
        this.y = y;
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

    public Piece[] getpieceStack() {
        return pieceStack.toArray(new Piece[0]);
    }

    public Piece getTop() {
        return (pieceStack.isEmpty()) ? null : pieceStack.peek();
    }

    public boolean isFree() {
        return getTop().getClass() == block.getClass() || getTop() == null;
    }

    public void insert(Piece p) {
        pieceStack.push(p);
        if (p.getClass() == block.getClass()) {height = height++;}
    }

    public void removeTop() {
        pieceStack.pop();
    }

    public boolean isAdjacent(Square s) {
        int dx = abs(x - s.getX());
        int dy = abs(y - s.getY());
        return (dx <= 1) && (dy <= 1) && ((dx + dy) != 0);
    }
}
