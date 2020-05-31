package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;

import java.util.ArrayList;
import java.util.List;

//TODO modifying the mediator during simulation might cause problems down the line
public class DefeatChecker {
    //divinityList is initialized during setup with a copy of each divinity
    private final List<Player> playerList;
    private final Board board;

    public DefeatChecker(List<Player> playerList, Board board) {
        this.playerList = playerList;
        this.board = board;
    }

    public void checkDefeat(AbstractTurn turn, Player player) throws LossException {
        Player potentialLoser = checkIfAllPlayersHaveWorkers();
        if (potentialLoser == null) {
            Board boardAlias = new Board(board);
            Divinity currentDivinity = player.getDivinity();
            currentDivinity.setBoard(boardAlias);
            //select worker from alias
            for (Worker worker : player.getWorkerList()) {
                if (simulate(turn, worker.getCoordinates(), boardAlias)) {
                    currentDivinity.setBoard(board);
                    return;
                }
            }
            currentDivinity.setBoard(board);
        } else {
            player = potentialLoser;
        }
        throw new LossException(player);
    }

    private boolean simulate(AbstractTurn turn, Coordinates workerCoordinates, Board b) {
        for (Action action : turn.getAvailableActions()) {
            //ONLY WORKS FOR GODPOWERS WHICH AFFECT ADJACENT SQUARES
            for (Square s : getAdjacentSquares(workerCoordinates, b)) {
                //there is no need to copy board or turn as they only get modified once a successful action is found
                if (turn.tryAction(workerCoordinates, action, s.getCoordinates())) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Square> getAdjacentSquares(Coordinates coordinates, Board b) {
        int sX = coordinates.getR();
        int sY = coordinates.getC();
        List<Square> adjacentSquares = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            int dX = rotatingVector(i);
            int dY = rotatingVector(i + 2);
            int cX = sX + dX;
            int cY = sY + dY;
            if ((cX >= 0) && (cX <= 4) && (cY >= 0) && (cY <= 4)) {
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
        } else return 99;
    }

    private Player checkIfAllPlayersHaveWorkers() {
        for (Player p : playerList) {
            if (p.getWorkerList().isEmpty()) {
                return p;
            }
        }
        return null;
    }
}