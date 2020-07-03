package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.commons.ReducedSquare;

public class Printer {

    /**
     * Prints the board.
     *
     * @param board the board to be printed
     */
    public static void printBoard(ReducedSquare[][] board){

        int n = 1;

        String legend = AnsiCode.PURPLE+ "BOARD LEGEND: E = empty; Wn = worker at level n; Dn = dome at level n; Bn = block at level n\n"+ AnsiCode.RESET;
        System.out.println(legend);

        for(int i = 0; i<5; i++) {

            for (int j = 0; j<5; j++) System.out.print("-------");
            System.out.println("------");

            for(int j = 0; j<5; j++){
                if(n<10){
                    System.out.print("|   " + n + "   ");
                }
                else{
                    System.out.print("|   " + n + "  ");
                }
                n++;
            }
            System.out.println("|");

            for(int j = 0; j<5; j++) {

                ReducedSquare square = board[i][j];
                String piece;
                int colour = -1;
                if (square.HasDome())
                    piece = "D";
                else if (square.getWorker() != null) {
                    piece = "W";
                    colour = square.getWorker().getColour();
                }
                else if (square.getHeight() != 0) {
                    piece = "B";
                }
                else piece = "E";

                if(piece == "E")
                    System.out.print("|   " + print(square, piece) + "   ");
                else
                    System.out.print("|   " + print(square, piece) + "  ");
            }

            System.out.println("|");
        }

        for (int j = 0; j<5; j++) System.out.print("-------");
        System.out.println("------");
    }


    private static String print(ReducedSquare square, String piece)
    {
        String result = piece;
        if (piece.equals("E")) {
            return piece;
        }
        if(piece.equals("W"))
        {
            result = addColour(result, square.getWorker().getColour());
        }
        result = result + square.getHeight();
        return result;
    }

    private static String addColour(String string, int colour)
    {
        switch (colour) {
            case 0:
                string = AnsiCode.RED + string + AnsiCode.RESET;
                break;
            case 1:
                string = AnsiCode.GREEN + string + AnsiCode.RESET;
                break;
            case 2:
                string = AnsiCode.BLUE + string + AnsiCode.RESET;
                break;
        }
        return string;
    }

}