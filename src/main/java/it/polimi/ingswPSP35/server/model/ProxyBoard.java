package it.polimi.ingswPSP35.server.model;

public class ProxyBoard extends Board {

    public ProxyBoard(Board b) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.matrix[i][j] = new SquareProxy(b.getSquare(i,j));
            }
        }
    }
}