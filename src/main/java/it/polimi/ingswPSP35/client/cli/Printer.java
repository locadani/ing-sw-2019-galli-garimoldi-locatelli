package it.polimi.ingswPSP35.client.cli;

import it.polimi.ingswPSP35.client.Client;

public class Printer {

    /**
     * Prints the board
     */
    public static void printboard(){

        String[][] board = new String[5][5];

        Client.clientboard();

        for(int i = 0; i<5; i++) {

            for(int j = 0; j<5; j++) {
                System.out.print("-------\n"+
                                 "|"+ board[i][j] +"|"+
                                 "|"+      "|" + " ");
            }

            System.out.println();
        }


    }


}