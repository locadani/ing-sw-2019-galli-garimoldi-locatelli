package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.*;

import java.util.List;

public class DefeatChecker implements Runnable{
    //divinityList is initialized during setup with a copy of each divinity
    private List<Divinity> divinityList;
    private Board boardAlias;
    private DivinityMediator divinityMediator;
    private Divinity currentDivinity;


    public DefeatChecker(List<Divinity> divinityList, DivinityMediator divinityMediator) {
        this.divinityList = divinityList;
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
        for (Action action : turn.getAvailableActions()) {
            for (Square s : getAdjacentSquares(workerSquare)) {
                if (turn.tryAction(action, worker, s)) {
                    if (simulate(turn, worker, s, boardCopy)) {
                        return true;
                    }
                    boardCopy = new ProxyBoard(b);
                    turnCopy = turn.copy();
                }
            }
        }
        return false;
    }

    private List<Square> getAdjacentSquares(Square s) {
    }

    @Override
    public void run() {

    }
}
