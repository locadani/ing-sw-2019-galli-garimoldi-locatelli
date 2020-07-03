package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;

import java.util.ArrayList;
import java.util.List;

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
        if (piecesFirst.size() != piecesSecond.size())
            return false;
        for(int i = 0; i < piecesFirst.size(); i++) {
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

    public static boolean turnsAreValid(Divinity mockedDivinity, List<List<Action>> validTurns) {
        AbstractTurn turn = mockedDivinity.getTurn();

        List<List<Action>> possibleTurns = findPossibleTurns(turn.copy(), new ArrayList<>());

        return possibleTurns.size() == validTurns.size() && possibleTurns.containsAll(validTurns);
    }


    //recursive depth first search
    private static List<List<Action>> findPossibleTurns (AbstractTurn t, ArrayList<List<Action>> record) {
        List<Action> availableActions = t.getAvailableActions();
        //if available actions contains end turn, add this sequence of moves to the record and continue exploring
        if (availableActions.contains(Action.ENDTURN)) {
            List<Action> sequence = t.getActionsTaken();
            sequence.add(Action.ENDTURN);
            record.add(sequence);
        }
        //copy AbstractTurn to allow further branching later
        AbstractTurn tcopy = t.copy();
        for (Action action : Action.values()) {
            //explore all possible actions except endturn, as it's already handled in the recursive calls
            if (action != Action.ENDTURN) {
                if (tcopy.tryAction(new Coordinates(1), action, new Coordinates(1))) {
                    //bifurcate
                    findPossibleTurns(tcopy, record);
                    tcopy = t.copy();
                }
            }
        }
        //once all possibilities have been explored, return the list of all possible sequences of actions
        //because "record" is shared by all calls, all returns except the one of the first call can be ignored
        return record;
    }
}
