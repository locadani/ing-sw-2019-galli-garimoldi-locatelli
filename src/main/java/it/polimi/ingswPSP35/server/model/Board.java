package it.polimi.ingswPSP35.server.model;

import java.util.ArrayList;

public class Board {
    private Square[][] matrix;

    public Board() {
        buildMatrix();
    }


    /**
     * initializes matrix with empty Squares
     *
     * @author Paolo Galli
     */
    private void buildMatrix() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = new Square(i, j);
            }
        }
    }

    Square getSquare(int x, int y) {
        return matrix[x][y];
    }

//    ArrayList<Square> getAdjacent(Square s){};
}
