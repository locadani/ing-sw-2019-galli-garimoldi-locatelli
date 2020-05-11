package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.Exceptions.ClientDisconnectedException;
import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.Exceptions.PlayerQuitException;
import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.*;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO dividere in metodi e gestire disconnessioni
public class Server {

    private static String message;
    private static TurnTick turnTick;
    private static Board board;
    private static int nPlayers;
    private static List<Player> players;
    private static List<String> chosenDivinities;
    private static String currentDivinity;
    private static DivinityMediator divinityMediator;
    private static List<String> colours;
    private static Winner winner;
    private static DefeatChecker defeatChecker;
    private static Thread connectionsChecker;


    public static void main(String[] args) {

        try
        {
            initializeConnectionsChecker();
            retrievePlayers();
            newMatchSetup();
            startNewMatch();
        }
        catch (PlayerQuitException e)
        {
            players.remove(e.getPlayer());
            View.removePlayer(e.getPlayer());
            View.notify(players, "Player " + e.getPlayer().getUsername() + " left the game");
        }
        catch (ClientDisconnectedException e)
        {
            View.notify(players, "Player " + e.getDisconnectedPlayer().getUsername() + " disconnected");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            View.notify(players, "TERMINATEMATCH");
        }

    }

    private static void initializeVariables()
    {
        board = new Board();
        message = "";
        turnTick = null;
        players = new ArrayList<>();
        chosenDivinities = new ArrayList<>();
        currentDivinity = "";
        divinityMediator = null;
        colours = new ArrayList<>(List.of("RED", "BLUE", "GREEN"));
        winner = new Winner();
    }

    private static void retrievePlayers()
    {
        initializeVariables();
        //Ask VView NPlayers and Players info
        System.out.println("Waiting for players");
        players = View.getPlayers();
        nPlayers = players.size();
        System.out.println("Received players");
    }

    private static void initializeConnectionsChecker()
    {

    }

    private static void newMatchSetup() throws IOException
    {
        //settings
        players.sort(new OrderByIncreasingAge());

        chooseMatchDivinities();

        chooseNDivinities();

        message = "Chosen divinites:\n";
        for(Player p: players)
        {
            message = message + p.getUsername() + " chose " + p.getDivinity().getName()+ "\n";
        }
        View.notify(players, message);

        setDivinityMediator();

        //TODO fare copia lista giocatori (abstract turn)
        defeatChecker = new DefeatChecker(new ArrayList<>(players),divinityMediator);

        //place Workers and set colour
        for (Player player : players) {

            chooseColour(player);

            placeWorker(0,player);
            placeWorker(1,player);
        }

        View.notify(players, "COMPLETEDSETUP");
    }

    private static void startNewMatch() throws PlayerQuitException, IOException
    {
        Player current;
        Iterator<Player> playerIterator = playerIterator = players.iterator();
        //Game starts
        turnTick = new TurnTick(winner, defeatChecker);

        while (winner.getWinner() == null) {
            try {

                current = playerIterator.next();
                turnTick.handleTurn(current);

                if (!playerIterator.hasNext())
                    playerIterator = players.iterator();
            }
            catch (LossException e)
            {
                deletePlayer(e.getLoser());
            }
        }
        View.notify(players, "Player " + getPlayerFromDivinity(winner.getWinner()) + " has won");
    }

    private static void deletePlayer(Player player)
    {
        if(player.getWorker(0)!= null)
            board.getSquare(player.getWorker(0).getCoordinates()).removeTop();
        if(player.getWorker(1)!= null)
            board.getSquare(player.getWorker(1).getCoordinates()).removeTop();
        ((DivinityMediatorDecorator) divinityMediator).removeDecorator(player.getDivinity().getName());
        players.remove(player);
    }

    private static void chooseMatchDivinities()
    {
        boolean performedAction = false;
        while(!performedAction)
        {
            try
            {
                chosenDivinities = View.getDivinities(players.get(0),nPlayers);
                performedAction = true;
            }
            catch (Exception e)
            {
                performedAction = false;
            }
        }
    }

    private static void chooseNDivinities()
    {
        boolean performedAction = false;
        Iterator<Player> playerIterator = players.listIterator(1);
        Player current;

        while(chosenDivinities.size()>1)
        {
            current = playerIterator.next();
            while(!performedAction) {
                try
                {
                    currentDivinity = View.chooseDivinity(current, chosenDivinities);
                    current.setDivinity(DivinityFactory.create(currentDivinity));
                    chosenDivinities.remove(currentDivinity);
                    performedAction = true;
                }
                catch (Exception e)
                {
                    performedAction = false;
                }
            }
            current.getDivinity().setBoard(board);
        }
        players.get(0).setDivinity(DivinityFactory.create(chosenDivinities.get(0)));
        players.get(0).getDivinity().setBoard(board);
    }

    private static void placeWorker(int i, Player player) throws IOException {
        Coordinates coordinates;
        boolean performedAction = false;

        while(!performedAction) {
            coordinates = View.getCoordinates(player);

            if(player.getDivinity().placeWorker(player.getWorker(i),coordinates))
            {
                player.getWorker(i).setCoordinates(coordinates);
                performedAction = true;
            }
            else
                View.notify(player,"The cell is invalid");
        }
    }

    private static void chooseColour(Player player) throws IOException {
        String chosenColour;
        do {
            chosenColour = View.chooseColour(player, colours);
            player.setColour(colours.indexOf(chosenColour));
        }while(colours.remove(chosenColour));
    }

    private static void setDivinityMediator() throws IOException {
        divinityMediator = new DivinityMediator();
        for(Player player : players)
        {
            divinityMediator = player.getDivinity().decorate(divinityMediator);
        }
        divinityMediator = new SentinelDecorator(divinityMediator);
        for(Player player : players)
        {
            player.getDivinity().setDivinityMediator(divinityMediator);
            player.getDivinity().setWinner(winner);
        }
    }

    private static Player getPlayerFromDivinity(Divinity playerDivinity)
    {
        for(Player player : players)
        {
            if(player.getDivinity().getName().equals(playerDivinity.getName()))
                return player;
        }
        return null;
    }
}