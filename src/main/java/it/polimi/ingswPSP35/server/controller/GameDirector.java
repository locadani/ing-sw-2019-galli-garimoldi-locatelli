package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.server.ClientHandler;
import it.polimi.ingswPSP35.server.VirtualView;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Coordinates;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameDirector {
    private VirtualView view;
    private List<Player> playerList;
    private DefeatChecker defeatChecker;

    public GameDirector (VirtualView view) {
        this.view = view;
        this.playerList = view.getPlayerList();
    }

    public void setup() {
        //sort players by age
        playerList.sort(Comparator.comparing(Player::getAge));
        assignDivinities();
        initializeHelperClasses();
        placeWorkers();
        view.broadcast(MessageID.FINISHEDSETUP, null);
    }

    private void assignDivinities() {
        //ask first player to select divinities
        Player firstPlayer = playerList.get(0);
        if (playerList.size() == 2) {
            view.sendToPlayer(firstPlayer, MessageID.CHOOSE2DIVINITIES, null);
        }
        else if (playerList.size() == 3) {
            view.sendToPlayer(firstPlayer, MessageID.CHOOSE3DIVINITIES, null);
        }
        else throw new IllegalArgumentException("number of players not supported");

        ArrayList<String> chosenDivinities = (ArrayList<String>) view.getAnswer(firstPlayer);
        //ask other players to pick their divinities from the list
        for (Player player : playerList) {
            if (!player.equals(firstPlayer)) {
                view.sendToPlayer(player, MessageID.PICKDIVINITY, chosenDivinities);
                String pickedDivinity = (String) view.getAnswer(player);
                player.setDivinity(DivinityFactory.create(pickedDivinity));
                chosenDivinities.remove(pickedDivinity);
            }
        }
        //assign the unselected divinity to the first player
        String lastDivinity = chosenDivinities.get(0);
        playerList.get(0).setDivinity(DivinityFactory.create(lastDivinity));
    }

    private void placeWorkers() {
        for (Player player : playerList) {
            int workersPlaced = 0;
            do {
                view.sendToPlayer(player, MessageID.PLACEWORKER, null);
                Coordinates chosenSquare = (Coordinates) view.getAnswer(player);
                if (player.getDivinity().placeWorker(new Worker(chosenSquare, player), chosenSquare)) {
                    workersPlaced++;
                }
                else view.sendNotificationToPlayer(player,"Invalid square selected, please select a valid square");
            } while (workersPlaced < 2);
        }
    }

    private void initializeHelperClasses() {

        DivinityMediator divinityMediator = new DivinityMediator();
        Winner winner = new Winner();
        Board board = new Board();

        for (Player player : playerList) {
            divinityMediator = player.getDivinity().decorate(divinityMediator);
        }

        divinityMediator = new SentinelDecorator(divinityMediator);
        for (Player player : playerList) {
            player.getDivinity().setDivinityMediator(divinityMediator);
            player.getDivinity().setWinner(winner);
            player.getDivinity().setBoard(board);
        }

        defeatChecker = new DefeatChecker(playerList, board);
    }


    public void playGame(){

    }
}
