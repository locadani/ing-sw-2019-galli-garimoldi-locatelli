package it.polimi.ingswPSP35.server.model;

import java.util.ArrayList;

public class Board {
    protected Square[][] matrix = new Square[5][5];

    /**
     * initializes matrix with empty Squares
     *
     * @author Paolo Galli
     */
    public Board() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.matrix[i][j] = new ConcreteSquare(i, j);
            }
        }
    }

    public Board (Board board) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = board.getSquare(i,j).copy();
            }
        }
    }

    public Square getSquare(int x, int y) {
        return matrix[x][y];
    }



//    ArrayList<Square> getAdjacent(Square s){};
}
