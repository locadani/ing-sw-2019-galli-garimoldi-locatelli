package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.controller.divinities.Action;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;

import java.util.ArrayList;
import java.util.List;

public class DefeatChecker {
    //TODO initialize deafeatChecker at setup to pass a Board reference to the shared board
    //divinityList is initialized during setup with a copy of each divinity
    private List<Player> playerList;
    private Board board;
    private DivinityMediator divinityMediator;
    private Player currentPlayer;


    public DefeatChecker(List<Player> playerList, DivinityMediator divinityMediator) {
        this.playerList = playerList;
        this.divinityMediator = divinityMediator;
    }

    public void checkDefeat(AbstractTurn Turn, Player player) throws LossException {
        Player potentialLoser = checkIfAllPlayersHaveWorkers();
        if (potentialLoser == null) {
            Board boardAlias = new Board(board);
            Divinity currentDivinity = player.getDivinity();
            //select worker from alias
            for (Worker worker : player.getWorkerList()) {
                //select corresponding worker from boardAlias
                Square workerSquare = boardAlias.getSquare(worker.getCoordinates());
                worker = (Worker) workerSquare.getTop();
                AbstractTurn turn = currentDivinity.getTurn();
                if (simulate(turn, worker, workerSquare, new ProxyBoard(boardAlias))) {
                    return;
                }
            }
        }
        else player = potentialLoser;
        throw new LossException(player);
    }

    private boolean simulate(AbstractTurn turn, Worker worker, Square workerSquare, Board b) {
        if (turn.getAvailableActions().contains(Action.ENDTURN)) {
            return true;
        }
        //TODO check if it's necessary to copy Turn and Board
        AbstractTurn turnCopy = turn.copy();
        Board boardCopy = new ProxyBoard(b);
        for (Action action : turn.getAvailableActions()) {
            //ONLY WORKS FOR GODPOWERS WHICH AFFECT ADJACENT SQUARES
            for (Square s : getAdjacentSquares(workerSquare, boardCopy)) {
                if (turn.tryAction(worker.getCoordinates(), action, s.getCoordinates())) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Square> getAdjacentSquares(Square s, Board b) {
        int sX = s.getR();
        int sY = s.getC();
        List<Square> adjacentSquares = new ArrayList<>(8);
        for (int i = 0; i<8; i++) {
            int dX = rotatingVector(i);
            int dY = rotatingVector(i + 2);
            int cX = sX + dX;
            int cY = sY + dY;
            if ((cX >= 0) && (cX <= 4) && (cY >= 0) && (cY <= 4)){
                adjacentSquares.add(b.getSquare(cX, cY)); 
            }
        }
        return adjacentSquares;
    }

    private int rotatingVector(int i) {
        if (i % 4 == 0) {
            return 0;
        } else if (i % 8 > 0 && i % 8 < 4) {
            return 1;
        } else if (i % 8 > 4) {
            return -1;
        }
        else return 99;
    }

    private Player checkIfAllPlayersHaveWorkers (){
        for (Player p : playerList) {
            if (p.getWorkerList().isEmpty()) {
                playerList.remove(p);
                return p;
            }
        }
        return null;
    }

}
