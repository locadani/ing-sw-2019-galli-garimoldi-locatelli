package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the board of the game. The board is made of 25 {@code Square}s and has a list of recently modified
 * {@code Square}s in order to communicate properly with the user interface.
 *
 * @author Paolo Galli
 *
 * @see Square
 * @see it.polimi.ingswPSP35.server.controller.DefeatChecker
 */
public class Board {
    protected Square[][] matrix;
    private List<Square> changedSquares;

    /**
     * Constructor that initializes the board with empty {@code Square}s.
     *
     */
    public Board() {
        changedSquares = new ArrayList<>();
        matrix = new Square[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.matrix[i][j] = new Square(i, j);
            }
        }
    }

    /**
     * Constructor that initializes with copies of the {@code Square}s of the input parameter, in the same place
     * of the one of the input parameter.
     *
     */
    public Board (Board board) {
        changedSquares = new ArrayList<>();
        matrix = new Square[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                 this.matrix[i][j] = board.getSquare(i,j).copy();
            }
        }
    }

    /**
     * Getter for a specific {@code Square} identified by the integers {@code r} and {@code c}.
     *
     * @param r number of row
     * @param c number of column
     * @return {@code Square} identified by {@code r} and {@code c}
     */
    public Square getSquare(int r, int c) {
        return matrix[r][c];
    }

    /**
     * Getter for a specific {@code Square} identified by {@code coordinates}.
     *
     * @param coordinates {@code Coordinates} corresponding to the desired square
     * @return {@code Square} identified by {@code coordinates}
     */
    public Square getSquare(Coordinates coordinates) {
        return matrix[coordinates.getR()][coordinates.getC()];
    }

    /**
     * Setter for the List of {@code Square}s that have been modified recently.
     * @param changedSquares {@code Square}s that have been modified recently
     */
    public void setChangedSquares(List<Square> changedSquares)
    {
        this.changedSquares.addAll(changedSquares);
    }

    /**
     * Getter for the List of {@code Square}s that have been modified recently. This list resets after this method is
     * called.
     * @return List of {@code Square}s that have been modified recently
     */
    public List<Square> getChangedSquares()
    {
        List<Square> toReturnSquares = new ArrayList<>(changedSquares);
        changedSquares.clear();
        return toReturnSquares;
    }
}
