package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.DisconnectedException;
import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.VirtualView;
import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;

import java.util.*;
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
            "Ares",
            "Athena",
            "Artemis",
            "Atlas",
            "Charon",
            "Demeter",
            "Hephaestus",
            "Hera",
            "Hestia",
            "Limus",
            "Minotaur",
            "Pan",
            "Prometheus");

    static List<String> colourList = List.of("RED", "GREEN", "BLUE");

    public GameDirector(VirtualView virtualView) {
        this.virtualView = virtualView;
        this.playerList = virtualView.getPlayerList();
    }

    public void setup() throws DisconnectedException {
        //sort players by age
        playerList.sort(Comparator.comparing(Player::getAge));
        assignDivinities();

        initializeGameClasses();
        placeWorkers();

        virtualView.broadcast(MessageID.FINISHEDSETUP, null);
    }

    private void assignDivinities() throws DisconnectedException {
        //TODO first player has to choose starter player after choosing divinities! starter player will then place workers
        //ask first player to select divinities
        Player challenger = playerList.get(0);
        if (playerList.size() == 2) {
            virtualView.sendToPlayer(challenger, MessageID.CHOOSE2DIVINITIES, allDivinities);
        } else if (playerList.size() == 3) {
            virtualView.sendToPlayer(challenger, MessageID.CHOOSE3DIVINITIES, allDivinities);
        } else throw new IllegalArgumentException("number of players not supported");

        ArrayList<String> chosenDivinities = (ArrayList<String>) virtualView.getAnswer(challenger);
        //ask other players to pick their divinities from the list
        for (Player player : playerList) {
            if (!player.equals(challenger)) {
                virtualView.sendToPlayer(player, MessageID.PICKDIVINITY, chosenDivinities);
                String pickedDivinity = (String) virtualView.getAnswer(player);
                player.setDivinity(DivinityFactory.create(pickedDivinity));
                chosenDivinities.remove(pickedDivinity);
            }
        }
        //assign the unselected divinity to the first player
        String lastDivinity = chosenDivinities.get(0);
        playerList.get(0).setDivinity(DivinityFactory.create(lastDivinity));

        //notify players of assigned divinities
        Map<String, String> userToDivinity = new HashMap<>(3);
        for(Player player : playerList) {
            userToDivinity.put(player.getUsername(), player.getDivinity().getName());
        }
        virtualView.broadcast(MessageID.DIVINITIESCHOSEN, userToDivinity);

        //ask challenger to choose the first player
        virtualView.sendToPlayer(challenger,MessageID.CHOOSEFIRSTPLAYER,
                playerList.stream().map(Player::getUsername).collect(Collectors.toList()));
        int chosenPlayerIndex = (int) virtualView.getAnswer(challenger);
        
        //rearrange the list so that initial order is preserved and the player chosen by the challenger
        //is shifted to the front
        List<Player> firstHalf = playerList.subList(0,chosenPlayerIndex);
        List<Player> secondHalf = playerList.subList(chosenPlayerIndex, playerList.size());
        playerList = new ArrayList<>(secondHalf);
        playerList.addAll(firstHalf);
    }

    private void placeWorkers() throws DisconnectedException {
        List<String> availableColours = new ArrayList<>(colourList);
        for (Player player : playerList) {
            //ask player for worker colour
            virtualView.sendToPlayer(player, MessageID.CHOOSECOLOUR, availableColours);
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

        DefeatChecker defeatChecker = new DefeatChecker(new ArrayList<>(playerList), board);
        turnTick = new TurnTick(defeatChecker, playerList);
    }


    public void playGame() throws DisconnectedException {
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
                //works correctly only if current is the loser, otherwise we need to bypass the iterator to avoid
                //ConcurrentModificationException
                Player loser = e.getLoser();
                deletePlayer(loser);
                playerIterator.remove();
                virtualView.broadcastNotification(loser.getUsername() + " has lost");
                virtualView.sendNotificationToPlayer(loser, "Disconnecting");
                virtualView.disconnect(loser);
                //if the loser was the last of the iterator, create a new iterator
                if (!playerIterator.hasNext())
                    playerIterator = playerList.iterator();
                current = playerIterator.next();
                //if all players have lost, the current player is the winner
                if(playerList.size() == 1)
                    winner.setWinner(playerList.get(0).getDivinity());
            }
        }

        virtualView.broadcastNotification("Player " + getPlayerFromDivinity(winner.getWinner()).getUsername() + " is victorious");
    }

    private void playTurn(Player player) throws LossException, DisconnectedException {

        boolean performedAction;
        RequestedAction requestedAction;

        virtualView.sendNotificationToPlayer(player, "It's your turn");

        //TODO call checkDefeat here instead of after "do"

        do {
            turnTick.checkDefeat(player);
            requestedAction = virtualView.performAction(player);
            performedAction = turnTick.handleTurn(player, requestedAction);

            if (performedAction) {
                virtualView.update(board.getChangedSquares().stream()
                        .map(Square::reduce)
                        .collect(Collectors.toList()));
                //TODO also call checkDefeat here (see below
                /*if (requestedAction.getAction() != Action.ENDTURN)
                *   turnTick.checkDefeat(player) */
            } else
                virtualView.sendNotificationToPlayer(player, "Action not valid, please select a valid action");

        } while (!(requestedAction.getAction() == Action.ENDTURN && performedAction) && winner.getWinner() == null);

        virtualView.sendNotificationToPlayer(player, "Your turn has ended");
    }

    private void deletePlayer(Player player) {
        List<Square> changedSquares = new ArrayList<>();
        for (Worker worker : player.getWorkerList()) {
            Square workerSquare = board.getSquare(worker.getCoordinates());
            if (workerSquare.getTop().equals(worker)) {
                workerSquare.removeTop();
                changedSquares.add(workerSquare);
            }
        }
        ((DivinityMediatorDecorator) divinityMediator).removeDecorator(player.getDivinity().getName());
        virtualView.update(changedSquares.stream()
                .map(Square::reduce)
                .collect(Collectors.toList()));
    }


    private Player getPlayerFromDivinity(Divinity playerDivinity) {
        for (Player player : playerList) {
            if (player.getDivinity().getName().equals(playerDivinity.getName()))
                return player;
        }
        return null;
    }
}
