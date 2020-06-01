package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.client.gui.Cell;
import it.polimi.ingswPSP35.commons.ReducedSquare;

import java.util.ArrayList;
import java.util.List;

public class ReducedBoard {

    private ReducedSquare[][] board = new ReducedSquare[5][5];

    public ReducedBoard() {
        for (int i = 0; i < 25; i++)
            board[i/5][i%5] = new ReducedSquare();
    }

    public ReducedSquare getReducedSquare(int r, int c)
    {
        return board[r][c];
    }

    public ReducedSquare getReducedSquare(int cell)
    {
        return board[cell / 5][cell % 5];
    }

    public void update(List<ReducedSquare> changedSquares) {
        for (ReducedSquare square : changedSquares) {
            board[square.getCoordinates().getR()][square.getCoordinates().getC()] = square;
        }
    }

    public ReducedSquare[][] getMatrix() {
        return board;
    }
}
