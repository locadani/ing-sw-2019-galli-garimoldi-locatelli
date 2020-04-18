package it.polimi.ingswPSP35.server.model;

import java.util.ArrayList;

public class SquareProxy extends Square{

    private Square concreteSquare;

    private boolean instantiated = false;

    private void instantiate() {
        if (!instantiated) {
            this.concreteSquare = concreteSquare.copy();
            instantiated = true;
        }
    }

    public SquareProxy(Square square) {
        super(square.getX(),square.getY());
        this.concreteSquare = square;
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public int getHeight() {
        return concreteSquare.getHeight();
    }

    @Override
    public ArrayList<Piece> getPieceStack() {
        if (!instantiated) {
            instantiate();
        }
        return concreteSquare.getPieceStack();
    }

    @Override
    public Piece getTop() {
        if (!instantiated) {
            instantiate();
        }
        return concreteSquare.getTop();
    }

    @Override
    public boolean isFree() {
        return concreteSquare.isFree();
    }

    @Override
    public void insert(Piece p) {
        if (!instantiated) {
            instantiate();
        }
        concreteSquare.insert(p);
    }

    @Override
    public void removeTop() {
        if(!instantiated) {
            instantiate();
        }
        concreteSquare.removeTop();
    }

    @Override
    public boolean isAdjacent(Square s) {
        return concreteSquare.isAdjacent(s);
    }
}
