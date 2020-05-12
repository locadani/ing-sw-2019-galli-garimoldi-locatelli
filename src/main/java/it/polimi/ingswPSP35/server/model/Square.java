package it.polimi.ingswPSP35.server.model;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public interface Square {

    int getR();

    int getC();

    Coordinates getCoordinates();

    int getHeight();

    ArrayList<Piece> getPieceStack();

    Piece getTop();

    boolean isFree();

    void insert(Piece p);

    void removeTop();

    boolean isAdjacent(Square s);

    Square copy();
}
