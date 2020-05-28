package it.polimi.ingswPSP35.client;

public class Printer {

    /**
     * Prints the board
     */
    public static void printBoard(CellInfo[][] board){

        int n = 1;

        String legend = AnsiCode.PURPLE+ "BOARD LEGEND: E = empty; Wn = worker at level n; Dn = dome at level n; Bn = block at level n\n"+ AnsiCode.RESET;
        System.out.println(legend);

        for(int i = 0; i<5; i++) {

            for (int j = 0; j<5; j++) System.out.print("-----");
            System.out.println("------");

            for(int j = 0; j<5; j++) {
                if((n<10 && board[i][j].getPiece() != "W" && board[i][j].getPiece() != "E")||(n>=10 &&(board[i][j].getPiece() == "W" || board[i][j].getPiece() == "E"))){
                    System.out.print("|" + n + "  " + board[i][j].print());
                    n++;}
                else if (n<10 && (board[i][j].getPiece() == "W"||board[i][j].getPiece() == "E")){
                    System.out.print("|" + n + "  " + board[i][j].print() + " ");
                    n++;}
                else{
                    System.out.print("|" + n + " " + board[i][j].print());
                    n++;
                }
            }

            System.out.println("|");
        }

        for (int j = 0; j<5; j++) System.out.print("-----");
        System.out.println("------");


    }

}