package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.controller.divinities.Action;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;

import java.util.ArrayList;
import java.util.List;

public class DefeatChecker implements Runnable{
    //divinityList is initialized during setup with a copy of each divinity
    private List<Divinity> divinityList;
    private Board boardAlias;
    private DivinityMediator divinityMediator;
    private Divinity currentDivinity;


    public DefeatChecker(List<Divinity> divinityList, DivinityMediator divinityMediator) {
        this.divinityList = divinityList;
        //WARNING: if players take turns very quickly the mediator might change during the defeat checking process
        //consider locking the mediator
        this.divinityMediator = divinityMediator;
    }

    private void createBoardAlias(Board board){
        boardAlias = new Board(board);
    }

    private Divinity setDivinity(String desiredDivinity) throws Exception {
        for (Divinity currentDivinity : divinityList) {
            if (currentDivinity.getName().equals(desiredDivinity)) {
                return currentDivinity;
            }
        }
        throw new Exception("divinity not found");
    }

    public boolean checkDefeat(Player player, Board board) {
        boardAlias = new Board(board);

        try {
            //select divinity copy from local list
            currentDivinity = setDivinity(player.getDivinity().getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        //select worker from alias
        for (Worker worker : player.getWorkerList()) {
            //select corresponding worker from boardAlias
            Square workerSquare = boardAlias.getSquare(worker.getX(), worker.getY());
            worker = (Worker) workerSquare.getTop();
            AbstractTurn turn = currentDivinity.getTurn();
            if(simulate(turn, worker, workerSquare, new ProxyBoard(boardAlias))){
                return true;
            }
        }
        //placeholder
        return true;
    }

    private boolean simulate(AbstractTurn turn, Worker worker, Square workerSquare, Board b) {
        if (turn.getAvailableActions().contains(Action.ENDTURN)) {
            return true;
        }
        AbstractTurn turnCopy = turn.copy();
        Board boardCopy = new ProxyBoard(b);
        //TODO optimize to give priority to build and move actions if not yet taken, as most divinities can end their turn after doing one of each
        for (Action action : turn.getAvailableActions()) {
            //ONLY WORKS FOR GODPOWERS WHICH AFFECT ADJACENT SQUARES
            for (Square s : getAdjacentSquares(workerSquare, boardCopy)) {
                if (turn.tryAction(action, worker, s)) {
                    if (simulate(turnCopy, worker, s, boardCopy)) {
                        return true;
                    }
                    boardCopy = new ProxyBoard(b);
                    turnCopy = turn.copy();
                }
            }
        }
        return false;
    }

    private List<Square> getAdjacentSquares(Square s, Board b) {
        int sX = s.getX();
        int sY = s.getY();
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

    @Override
    public void run() {

    }
}
