package it.polimi.ingswPSP35.server.model;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public abstract class Square {
    private int x;
    private int y;

    public abstract int getR();

    public abstract int getC();

    public abstract int getHeight();

    public abstract ArrayList<Piece> getPieceStack();

    public abstract Piece getTop();

    public abstract boolean isFree();

    public abstract void insert(Piece p);

    public abstract void removeTop();

    public abstract boolean isAdjacent(Square s);

    public abstract Square copy();
}
