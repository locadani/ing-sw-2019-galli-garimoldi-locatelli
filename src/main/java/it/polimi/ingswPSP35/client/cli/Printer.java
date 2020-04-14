package it.polimi.ingswPSP35.client.cli;

public class Printer {

    /**
     * Prints the board
     */
    public static void printboard(){

        int[][] board = new int [5][5];

        //Board()

        for(int i = 0; i<5; i++) {

            for(int j = 0; j<5; j++) {
                System.out.print(board [i][j] + " ");
            }

            System.out.println();
        }


    }
}