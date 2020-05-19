package it.polimi.ingswPSP35.server.model;

public class DebugBoard extends Board {

    public DebugBoard() {
        super();
    }

    public void printBoard() {
        System.out.println("------------------------------");
        for (int i = 0; i < 5; i++) {
            String row = "";
            for (int j = 0; j < 5; j++) {
                Piece top = matrix[i][j].getTop();
                if (top instanceof Worker) {
                    row = row + "W"
                            + ((Worker) top).getPlayer().getUsername().charAt(0)
                            + " " + matrix[i][j].getHeight() + " |";
                } else if (top instanceof Dome) {
                    row = row + "D  " + matrix[i][j].getHeight() + " |";
                } else row = row + "   " + matrix[i][j].getHeight() + " |";
            }
            System.out.println(row);
            System.out.println("------------------------------");
        }
        System.out.println();
    }
}
