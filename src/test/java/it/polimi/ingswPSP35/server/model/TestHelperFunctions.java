package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.Coordinates;
import java.util.ArrayList;

import static java.lang.Math.min;

public class TestHelperFunctions {

    public static boolean boardEquals(Board first, Board second) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!squareEquals(first.getSquare(i, j), second.getSquare(i, j)))
                    return false;
            }
        }
        //if all match, return true
        return true;
    }

    public static boolean squareEquals(Square first, Square second) {
        ArrayList<Piece> piecesFirst = first.getPieceStack();
        ArrayList<Piece> piecesSecond = second.getPieceStack();
        for(int i = 0; i < (min(piecesFirst.size(), piecesSecond.size())); i++) {
            Piece pieceFirst = piecesFirst.get(i);
            Piece pieceSecond = piecesSecond.get(i);
            if ((pieceFirst instanceof Worker)
                    && (pieceSecond instanceof Worker)
                    && !workerEquals((Worker) pieceFirst, (Worker) pieceSecond)) return false;
            else if (pieceFirst instanceof Block && pieceSecond instanceof Dome
                    || pieceFirst instanceof Dome && pieceSecond instanceof Block)
                return false;
        }
        //if all match, return true
        return true;
    }

    public static boolean workerEquals(Worker first, Worker second) {
        return first.getPlayer().equals(second.getPlayer())
                && coordinatesEquals(first.getCoordinates(), second.getCoordinates());
    }

    public static boolean coordinatesEquals(Coordinates first, Coordinates second) {
        return first.getC() == second.getC()
                && first.getR() == second.getR();
    }

    public static void printBoard(Board board) {
        System.out.println("------------------------------");
        for (int i = 0; i < 5; i++) {
            String row = "";
            for (int j = 0; j < 5; j++) {
                Piece top = board.getSquare(i,j).getTop();
                if (top instanceof Worker) {
                    row = row + "W"
                            + ((Worker) top).getPlayer().getUsername().charAt(0)
                            + " " + board.getSquare(i,j).getHeight() + " |";
                } else if (top instanceof Dome) {
                    row = row + "D  " + board.getSquare(i,j).getHeight() + " |";
                } else row = row + "   " + board.getSquare(i,j).getHeight() + " |";
            }
            System.out.println(row);
            System.out.println("------------------------------");
        }
        System.out.println();
    }
}
