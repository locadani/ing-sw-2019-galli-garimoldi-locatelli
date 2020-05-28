package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.client.gui.Cell;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private CellInfo[][] board = new CellInfo[5][5];
    private List<Integer> modifiedCells;

    public Board() {
        modifiedCells = new ArrayList<>();
        for (int i = 0; i < 25; i++)
            board[i/5][i%5] = new CellInfo();
    }

    public CellInfo getCellInfo(int r, int c)
    {
        return board[r][c];
    }

    public CellInfo getCellInfo(int cell)
    {
        return board[cell / 5][cell % 5];
    }

    public void update(String[] params) {
        int r, c, height, colour;
        String piece;
        r = Integer.parseInt(params[1]);
        c = Integer.parseInt(params[2]);
        height = Integer.parseInt(params[3]);
        if (params.length > 4) {
            piece = params[4];
            if (piece.equals("WORKER")) {
                colour = Integer.parseInt(params[5]);
                modifyBoard(r, c, height, getCode(piece), colour);
            } else
                modifyBoard(r, c, height, getCode(piece));

        } else
            modifyBoard(r, c, height, "");
        int cell = r*5 + c;
        modifiedCells.add(cell);
    }


    /**
     * Applies changes to board
     * @param r      row to modify
     * @param c      column to modify
     * @param height height of the piece
     * @param piece  piece to place
     */
    private void modifyBoard(int r, int c, int height, String piece) {
        board[r][c].setHeight(height);
        board[r][c].setPiece(piece);
    }

    /**
     * Applies changes to board
     * @param r      row to modify
     * @param c      column to modify
     * @param height height of the piece
     * @param piece  piece to place
     * @param colour colour that represents player
     */
    private void modifyBoard(int r, int c, int height, String piece, int colour) {
        modifyBoard(r, c, height, piece);
        board[r][c].setColour(colour);
    }

    /**
     * Get code to place on board that identifies the piece
     * @param piece  piece to be represented
     * @param height height of the piece
     * @param colour colour that represents player
     * @return code associated to piece
     */
    private String getCode(String piece, int height, int colour) {
        String result = "";
        result = getCode(piece, height);
        switch (colour) {
            case 0:
                result = AnsiCode.RED + result + AnsiCode.RESET;
                break;
            case 1:
                result = AnsiCode.GREEN + result + AnsiCode.RESET;
                break;
            case 2:
                result = AnsiCode.BLUE + result + AnsiCode.RESET;
                break;
        }
        result = result + height;
        return result;
    }

    /**
     * Get code to place on board that identifies the piece
     * @param piece  piece to be represented
     * @param height height of the piece
     * @return code associated to piece
     */
    private String getCode(String piece, int height) {
        String result = "";
        switch (piece) {
            case "DOME":
                result = "D" + height;
                break;

            case "WORKER":
                result = "W";
                break;

            case "BLOCK":
                result = "B" + height;
                break;

            case "":
                result = "E";

        }
        return result;
    }

    private String getCode(String piece) {
        String result = "";
        switch (piece) {
            case "DOME":
                result = "D";
                break;

            case "WORKER":
                result = "W";
                break;

            case "BLOCK":
                result = "B";
                break;

            case "":
                result = "E";

        }
        return result;
    }

    public CellInfo[][] getMatrix()
    {
        return board;
    }

    public List<Integer> getModifiedCells()
    {
        return modifiedCells;
    }

}
