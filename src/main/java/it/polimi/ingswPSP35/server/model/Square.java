package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.ReducedSquare;

import java.util.ArrayList;

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

    ReducedSquare reduce();


    public boolean isPerimetral();
}
