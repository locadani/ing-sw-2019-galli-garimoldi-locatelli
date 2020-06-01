package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.VirtualView;
import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GameDirector {
    private VirtualView virtualView;
    private List<Player> playerList;
    private DivinityMediator divinityMediator;
    private TurnTick turnTick;
    private Board board;
    private Winner winner;


    static List<String> allDivinities = List.of(
            "Apollo",
            "Athena",
            "Artemis",
            "Atlas",
            "Demeter",
            "Hephaestus",
            "Minotaur",
            "Pan",
            "Prometheus");

    static List<String> colourList = List.of("RED", "GREEN", "BLUE");

    public GameDirector(VirtualView virtualView) {
        this.virtualView = virtualView;
        this.playerList = virtualView.getPlayerList();
    }

    public void setup() {
        //sort players by age
        playerList.sort(Comparator.comparing(Player::getAge));
        assignDivinities();
        initializeGameClasses();
        placeWorkers();
        virtualView.broadcast(MessageID.FINISHEDSETUP, null);
    }

    private void assignDivinities() {
        //TODO first player has to choose starter player after choosing divinities! starter player will then place workers
        //ask first player to select divinities
        Player firstPlayer = playerList.get(0);
        if (playerList.size() == 2) {
            virtualView.sendToPlayer(firstPlayer, MessageID.CHOOSE2DIVINITIES, allDivinities);
        } else if (playerList.size() == 3) {
            virtualView.sendToPlayer(firstPlayer, MessageID.CHOOSE3DIVINITIES, allDivinities);
        } else throw new IllegalArgumentException("number of players not supported");

        ArrayList<String> chosenDivinities = (ArrayList<String>) virtualView.getAnswer(firstPlayer);
        //ask other players to pick their divinities from the list
        for (Player player : playerList) {
            if (!player.equals(firstPlayer)) {
                virtualView.sendToPlayer(player, MessageID.PICKDIVINITY, chosenDivinities);
                String pickedDivinity = (String) virtualView.getAnswer(player);
                player.setDivinity(DivinityFactory.create(pickedDivinity));
                chosenDivinities.remove(pickedDivinity);
            }
        }
        //assign the unselected divinity to the first player
        String lastDivinity = chosenDivinities.get(0);
        playerList.get(0).setDivinity(DivinityFactory.create(lastDivinity));
        //TODO notify players with each chosen divinity
    }

    private void placeWorkers() {
        List<String> availableColours = new ArrayList<>(colourList);
        for (Player player : playerList) {
            //ask player for worker colour
            virtualView.sendToPlayer(player, MessageID.CHOOSECOLOUR, availableColours);
            //TODO maybe cleanup later
            int chosenColour = ((Integer) virtualView.getAnswer(player));
            int colour = colourList.indexOf(availableColours.get(chosenColour));
            player.setColour(colour);
            availableColours.remove(chosenColour);

            int workersPlaced = 0;
            do {
                virtualView.sendToPlayer(player, MessageID.PLACEWORKER, null);
                Coordinates chosenSquare = (Coordinates) virtualView.getAnswer(player);
                Worker worker = new Worker(chosenSquare, player);
                if (player.getDivinity().placeWorker(worker, chosenSquare)) {
                    workersPlaced++;
                    player.addWorker(worker);
                    virtualView.update(board.getChangedSquares().stream()
                            .map(Square::reduce)
                            .collect(Collectors.toList()));
                } else
                    virtualView.sendNotificationToPlayer(player, "Invalid square selected, please select a valid square");
            } while (workersPlaced < 2);
        }
    }

    private void initializeGameClasses() {

        divinityMediator = new DivinityMediator();
        winner = new Winner();
        board = new Board();

        for (Player player : playerList) {
            divinityMediator = player.getDivinity().decorate(divinityMediator);
        }

        divinityMediator = new SentinelDecorator(divinityMediator);
        for (Player player : playerList) {
            player.getDivinity().setDivinityMediator(divinityMediator);
            player.getDivinity().setWinner(winner);
            player.getDivinity().setBoard(board);
        }

        DefeatChecker defeatChecker = new DefeatChecker(playerList, board);
        turnTick = new TurnTick(defeatChecker, playerList);
    }


    public void playGame() {
        Iterator<Player> playerIterator = playerList.iterator();
        Player current = playerIterator.next();

        //Game starts
        while (winner.getWinner() == null) {
            try {
                playTurn(current);

                if (!playerIterator.hasNext())
                    playerIterator = playerList.iterator();
                current = playerIterator.next();

            } catch (LossException e) {
                Player loser = e.getLoser();
                deletePlayer(loser);
                virtualView.broadcastNotification(loser.getUsername() + " has lost");
                virtualView.sendNotificationToPlayer(loser, "Disconnecting");
                virtualView.disconnect(loser);
                //if the current player lost, select the next player
                if (loser.equals(current)) {
                    if (!playerIterator.hasNext())
                        playerIterator = playerList.iterator();
                    current = playerIterator.next();
                }
                //if all players have lost, the current player is the winner
                if(playerList.size() == 1)
                    winner.setWinner(playerList.get(0).getDivinity());
            }
        }

        virtualView.broadcastNotification("Player " + getPlayerFromDivinity(winner.getWinner()) + " is victorious");
    }

    private void playTurn(Player player) throws LossException {

        boolean performedAction;
        RequestedAction requestedAction;

        do {
            requestedAction = virtualView.performAction(player);
            performedAction = turnTick.handleTurn(player, requestedAction);

            if (performedAction) {
                virtualView.update(board.getChangedSquares().stream()
                        .map(Square::reduce)
                        .collect(Collectors.toList()));
            } else
                virtualView.sendNotificationToPlayer(player, "Action not valid, please select a valid action");

        } while (!(requestedAction.getAction() == Action.ENDTURN && performedAction) && winner.getWinner() == null);
    }

    private void deletePlayer(Player player) {
        for (Worker worker : player.getWorkerList()) {
            Square workerSquare = board.getSquare(player.getWorker(0).getCoordinates());
            if (workerSquare.getTop().equals(worker)) {
                workerSquare.removeTop();
            }
        }
        ((DivinityMediatorDecorator) divinityMediator).removeDecorator(player.getDivinity().getName());
        playerList.remove(player);
    }


    private Player getPlayerFromDivinity(Divinity playerDivinity) {
        for (Player player : playerList) {
            if (player.getDivinity().getName().equals(playerDivinity.getName()))
                return player;
        }
        return null;
    }
}
