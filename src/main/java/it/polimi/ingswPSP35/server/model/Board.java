package it.polimi.ingswPSP35.server.model;


import it.polimi.ingswPSP35.server.VView.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board {
    protected Square[][] matrix;
    private List<Square> changedSquares;

    /**
     * initializes matrix with empty Squares
     *
     * @author Paolo Galli
     */
    public Board() {
        changedSquares = new ArrayList<>();
        matrix = new Square[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.matrix[i][j] = new ConcreteSquare(i, j);
            }
        }
    }

    public Board (Board board) {
        changedSquares = new ArrayList<>();
        matrix = new Square[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = board.getSquare(i,j).copy();
            }
        }
    }

    public Square getSquare(int r, int c) {
        return matrix[r][c];
    }

    public Square getSquare(Coordinates coordinates) {
        return matrix[coordinates.getR()][coordinates.getC()];
    }

    public void setChangedSquares(List<Square> changedSquares)
    {
        this.changedSquares.addAll(changedSquares);
    }

    public List<Square> getChangedSquares()
    {
        List<Square> toReturnSquares = new ArrayList<>(changedSquares);
        changedSquares.clear();
        return toReturnSquares;
    }
}
