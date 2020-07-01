package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.ReducedSquare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static java.lang.Math.abs;

public class Square {
    private final Coordinates coordinates;
    private int height = 0;
    private final Stack<Piece> pieceStack;

    Square(Coordinates coordinates) {
        this.coordinates = coordinates;
        pieceStack = new Stack<Piece>();
    }

    Square(int r, int c) {
        this.coordinates = new Coordinates(r, c);
        pieceStack = new Stack<Piece>();
    }


    public int getC() {
        return coordinates.getC();
    }

    public int getR() {
        return coordinates.getR();
    }

    public Coordinates getCoordinates() {
        return coordinates.copy();
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Piece> getPieceStack() {
        Piece[] piecesToCopy = pieceStack.toArray(new Piece[0]);
        ArrayList<Piece> arrayList = new ArrayList<Piece>(Arrays.asList(piecesToCopy));
        if (!arrayList.isEmpty()) {
            Piece top = arrayList.get(arrayList.size() - 1);
            if (top instanceof Worker) {
                arrayList.set(arrayList.size() - 1, ((Worker) top).copy());
            }
        }
        return arrayList;
    }

    public Piece getTop() {
        return (pieceStack.isEmpty()) ? null : pieceStack.peek();
    }

    public boolean isFree() {
        return getTop() == null || getTop() instanceof Block;
    }

    public void insert(Piece p) {
        pieceStack.push(p);
        if (p instanceof Block) {
            height++;
        }
    }

    public void removeTop() {
        if (!pieceStack.isEmpty()) {
            Piece top = pieceStack.peek();
            if (top instanceof Block)
                height--;
            pieceStack.pop();
        }
    }

    public boolean isAdjacent(Square s) {
        int dx = abs(coordinates.getR() - s.getR());
        int dy = abs(coordinates.getC() - s.getC());
        return (dx <= 1)
                && (dy <= 1)
                && (dx != 0 || dy != 0); //checks that s is not being compared to itself
    }

    public boolean isAdjacent(Coordinates c) {
        int dx = abs(coordinates.getR() - c.getR());
        int dy = abs(coordinates.getC() - c.getC());
        return (dx <= 1)
                && (dy <= 1)
                && (dx != 0 || dy != 0); //checks that s is not being compared to itself
    }

    public Square copy() {
        Square copy = new Square(coordinates);
        ArrayList<Piece> piecesToCopy = this.getPieceStack();
        for (Piece piece : piecesToCopy) {
            copy.insert(piece);
        }
        return copy;
    }

    public ReducedSquare reduce() {
        return new ReducedSquare(this);
    }

    public boolean isPerimetral() {
        return coordinates.getR() == 0 || coordinates.getR() == 4 || coordinates.getC() == 0 || coordinates.getC() == 4;
    }
}
