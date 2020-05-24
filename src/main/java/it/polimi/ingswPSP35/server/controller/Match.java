package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.Exceptions.DisconnectedException;
import it.polimi.ingswPSP35.server.Exceptions.LossException;
import it.polimi.ingswPSP35.server.Exceptions.PlayerQuitException;
import it.polimi.ingswPSP35.server.OrderByIncreasingAge;
import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.divinities.Action;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Coordinates;
import it.polimi.ingswPSP35.server.model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Match {

    private List<String> colours;
    private TurnTick turnTick;
    private Board board;
    private int nPlayers;
    private List<Player> players;
    private List<String> chosenDivinities;
    private String currentDivinity;
    private DivinityMediator divinityMediator;
    private DefeatChecker defeatChecker;
    private Winner winner;
    private View view;

    public Match(View view, List<Player> players) {
        this.view = view;
        this.players = players;
    }

    public void start() {
        try {
            try {
                initializeVariables();
                newMatchSetup();
                startNewMatch();
            }
            catch (PlayerQuitException e) {
                players.remove(e.getPlayer());
                view.removePlayer(e.getPlayer());
                view.notify(players, "Player " + e.getPlayer().getUsername() + " left the game");
                view.notify(players, "TERMINATEMATCH");
            }
        }
        catch (DisconnectedException e) {
            int i = getPlayerIndex(e.getName());
            System.out.println("E.getName: " + e.getName());
            removePlayer(i);
            try {
                view.notify(players, "The match is over, " + e.getName() + " disconnected");
            }
            catch (DisconnectedException e1) {
                System.out.println("It was impossible to notify players");
            }
        }
    }

    private void initializeVariables() {
        board = new Board();
        turnTick = null;
        chosenDivinities = new ArrayList<>();
        currentDivinity = "";
        divinityMediator = null;
        colours = new ArrayList<>(List.of("RED", "BLUE", "GREEN"));
        winner = new Winner();
        nPlayers = players.size();
    }

    private void newMatchSetup() throws DisconnectedException {
        String message;

        //settings
        players.sort(new OrderByIncreasingAge());

        chooseMatchDivinities();

        chooseNDivinities();

        message = "Chosen divinites ->\n";
        for (Player p : players) {
            message = message + p.getUsername() + " chose " + p.getDivinity().getName() + "\n";
        }
        view.notify(players, message);

        setDivinityMediator();

        //TODO fare copia lista giocatori (abstract turn)
        defeatChecker = new DefeatChecker(listDeepCopy(players), board);

        List<String> coloursCopy = new ArrayList<>(colours);


        //place Workers and set colour
        for (Player player : players) {

            chooseColour(player, coloursCopy);

            placeWorker(0, player);
            view.update(board.getChangedSquares());

            placeWorker(1, player);
            view.update(board.getChangedSquares());
        }

        view.notify(players, "COMPLETEDSETUP");
    }

    private void startNewMatch() throws PlayerQuitException, DisconnectedException {
        Player current;
        Iterator<Player> playerIterator = playerIterator = players.iterator();
        //Game starts
        turnTick = new TurnTick(winner, defeatChecker, players);


        while (winner.getWinner() == null) {
            try {

                current = playerIterator.next();
                playTurn(current);

                if (!playerIterator.hasNext())
                    playerIterator = players.iterator();
            }
            catch (LossException e) {
                deletePlayer(e.getLoser());
            }
        }

        view.notify(players, "Player " + getPlayerFromDivinity(winner.getWinner()) + " has won");
    }

    private void playTurn(Player player) throws PlayerQuitException, LossException, DisconnectedException {

        boolean performedAction = false;
        RequestedAction requestedAction = null;

        do {
            requestedAction = view.performAction(player);
            if (requestedAction.getAction() == Action.QUIT)
                throw new PlayerQuitException(player);
            performedAction = turnTick.handleTurn(player, requestedAction);
            if (performedAction) {

                view.update(board.getChangedSquares());
                view.notify(player, "Action Successful");
            } else
                view.notify(player, "Action Not Successful");

        } while (!(requestedAction.getAction() == Action.ENDTURN && performedAction) && winner.getWinner() == null);
    }

    private void deletePlayer(Player player) {
        if (player.getWorker(0) != null)
            board.getSquare(player.getWorker(0).getCoordinates()).removeTop();
        if (player.getWorker(1) != null)
            board.getSquare(player.getWorker(1).getCoordinates()).removeTop();
        ((DivinityMediatorDecorator) divinityMediator).removeDecorator(player.getDivinity().getName());
        players.remove(player);
    }

    private void chooseMatchDivinities() throws DisconnectedException {
        boolean performedAction = false;
        while (!performedAction) {
            chosenDivinities = view.getDivinities(players.get(0), nPlayers);
            performedAction = true;
        }
    }

    private void chooseNDivinities() throws DisconnectedException {
        boolean performedAction = false;
        Iterator<Player> playerIterator = players.listIterator(1);
        Player current = players.get(0);

        while (chosenDivinities.size() > 1) {
            current = playerIterator.next();
            currentDivinity = view.chooseDivinity(current, chosenDivinities);
            current.setDivinity(DivinityFactory.create(currentDivinity));
            chosenDivinities.remove(currentDivinity);
            current.getDivinity().setBoard(board);
        }
        players.get(0).setDivinity(DivinityFactory.create(chosenDivinities.get(0)));
        players.get(0).getDivinity().setBoard(board);
    }

    private void setDivinityMediator() {
        divinityMediator = new DivinityMediator();
        for (Player player : players) {
            divinityMediator = player.getDivinity().decorate(divinityMediator);
        }
        divinityMediator = new SentinelDecorator(divinityMediator);
        for (Player player : players) {
            player.getDivinity().setDivinityMediator(divinityMediator);
            player.getDivinity().setWinner(winner);
        }
    }

    private void placeWorker(int i, Player player) throws DisconnectedException {
        Coordinates coordinates;
        boolean performedAction = false;

        while (!performedAction) {
            coordinates = view.getCoordinates(player);
            System.out.println("Coordinates: " + coordinates.getR() + ", " + coordinates.getC());

            if (player.getDivinity().placeWorker(player.getWorker(i), coordinates)) {
                player.getWorker(i).setCoordinates(coordinates);
                performedAction = true;
            } else
                view.notify(player, "The cell is invalid");
        }
    }

    private void chooseColour(Player player, List<String> coloursCopy) throws DisconnectedException {
        String chosenColour;
        do {
            chosenColour = view.chooseColour(player, coloursCopy);
            player.setColour(colours.indexOf(chosenColour));
        } while (!coloursCopy.remove(chosenColour));
    }

    private List<Player> listDeepCopy(List<Player> toCopy) {
        List<Player> newList = new ArrayList<>();
        for (Player player : toCopy) {
            newList.add(player.clone());
        }
        return newList;
    }

    private Player getPlayerFromDivinity(Divinity playerDivinity) {
        for (Player player : players) {
            if (player.getDivinity().getName().equals(playerDivinity.getName()))
                return player;
        }
        return null;
    }

    private int getPlayerIndex(String username) {
        int i = 0;
        System.out.println("Entered username: " + username);
        for (Player player : players) {
            System.out.println("Current username: " + player.getUsername());
            if (player.getUsername().equals(username))
                return i;
            else
                i++;
        }
        return -1;
    }

    private void removePlayer(int i) {
        players.remove(i);
    }
}
