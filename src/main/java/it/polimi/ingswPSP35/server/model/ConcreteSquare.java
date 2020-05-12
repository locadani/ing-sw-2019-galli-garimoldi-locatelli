package it.polimi.ingswPSP35.server.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static java.lang.Math.abs;

public class ConcreteSquare implements Square {
    private final Coordinates coordinates;
    private int height = 0;
    private final Stack<Piece> pieceStack;

    private static final Block block = new Block();
    private static Dome dome;
    private static Worker worker;

    ConcreteSquare(Coordinates coordinates) {
        this.coordinates = coordinates;
        pieceStack = new Stack<Piece>();
    }

    ConcreteSquare(int r, int c) {
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
                arrayList.add(arrayList.size() - 1, ((Worker) top).copy());
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
        if (p.getClass() == block.getClass()) {
            height++;
        }
    }

    public void removeTop() {
        Piece top = pieceStack.peek();
        if(top instanceof Block)
            height--;
        pieceStack.pop();
    }

    public boolean isAdjacent(Square s) {
        int dx = abs(coordinates.getR() - s.getR());
        int dy = abs(coordinates.getC() - s.getC());
        return (dx <= 1)
                && (dy <= 1)
                && (dx != 0 || dy != 0); //checks that s is not being compared to itself
    }

    public ConcreteSquare copy() {
        ConcreteSquare copy = new ConcreteSquare(coordinates);
        ArrayList<Piece> piecesToCopy = this.getPieceStack();
        for (Piece piece : piecesToCopy) {
            copy.insert(piece);
        }
        return copy;
    }
}
