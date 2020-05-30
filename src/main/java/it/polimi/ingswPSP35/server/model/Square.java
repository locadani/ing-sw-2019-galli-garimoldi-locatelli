package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.ReducedSquare;
import it.polimi.ingswPSP35.commons.ReducedWorker;

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
}
