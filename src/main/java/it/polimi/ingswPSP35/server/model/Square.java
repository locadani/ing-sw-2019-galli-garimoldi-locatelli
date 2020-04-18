package it.polimi.ingswPSP35.server.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static java.lang.Math.abs;

public class Square {
    private final int x;
    private final int y;
    private int height = 0;
    private Stack<Piece> pieceStack;

    private static Block block = new Block();
    private static Dome dome;
    private static Worker worker;

    Square(int x, int y) {
        this.x = x;
        this.y = y;
        pieceStack = new Stack<Piece>();
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

    public ArrayList<Piece> getPieceStack() {
        Piece[] piecesToCopy = pieceStack.toArray(new Piece[0]);
        ArrayList<Piece> arrayList = new ArrayList<Piece>(Arrays.asList(piecesToCopy));
        Piece top = arrayList.get(arrayList.size() - 1);
            if (top instanceof Worker) {
                arrayList.add(arrayList.size() - 1, ((Worker) top).copy());
            }
        return arrayList;
    }

    public Piece getTop() {
        return (pieceStack.isEmpty()) ? null : pieceStack.peek();
    }

    public boolean isFree() {
        return getTop().getClass() == block.getClass() || getTop() == null;
    }

    public void insert(Piece p) {
        pieceStack.push(p);
        if (p.getClass() == block.getClass()) {
            height++;
        }
    }

    public void removeTop() {
        pieceStack.pop();
    }

    public boolean isAdjacent(Square s) {
        int dx = abs(x - s.getX());
        int dy = abs(y - s.getY());
        return (dx <= 1)
                && (dy <= 1)
                && (dx != 0 || dy != 0); //checks that s is not being compared to itself
    }

    public Square copy() {
        Square copy = new Square(this.getX(), this.getY());
        ArrayList<Piece> piecesToCopy = this.getPieceStack();
        for (Piece piece : piecesToCopy) {
            copy.insert(piece);
        }
        return copy;
    }
}
