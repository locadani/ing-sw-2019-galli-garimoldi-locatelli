package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;

public class DefeatChecker implements Runnable{
    //divinityList is initialized during setup with a copy of each divinity
    private List<Divinity> divinityList;
    private Board boardAlias;
    private DivinityMediator divinityMediator;

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
        Divinity currentDivinity;
        try {
            currentDivinity = setDivinity(player.getDivinity().getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        for (Worker worker : player.getWorkerList()) {
            Square square = boardAlias.getSquare(worker.getX(), worker.getY());
            worker = (Worker) square.getTop();

        }
        //placeholder
        return false;
    }

    @Override
    public void run() {

    }
}
