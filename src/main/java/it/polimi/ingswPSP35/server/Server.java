package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.Exceptions.ClientDisconnectedException;
import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.Exceptions.PlayerQuitException;
import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.*;
import it.polimi.ingswPSP35.server.controller.divinities.Action;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;

import javax.swing.*;
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
    private static View view = new View();


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
            view.removePlayer(e.getPlayer());
            view.notify(players, "Player " + e.getPlayer().getUsername() + " left the game");
        }
        catch (ClientDisconnectedException e)
        {
            view.notify(players, "Player " + e.getDisconnectedPlayer().getUsername() + " disconnected");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            view.notify(players, "TERMINATEMATCH");
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
        players = view.getPlayers();
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
        view.notify(players, message);

        setDivinityMediator();

        //TODO fare copia lista giocatori (abstract turn)
        defeatChecker = new DefeatChecker(listDeepCopy(players),divinityMediator);

        //place Workers and set colour
        for (Player player : players) {

            chooseColour(player);

            placeWorker(0,player);
            placeWorker(1,player);
        }

        view.notify(players, "COMPLETEDSETUP");
    }

    private static void startNewMatch() throws PlayerQuitException, IOException
    {
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
            catch (LossException e)
            {
                deletePlayer(e.getLoser());
            }
        }
        view.notify(players, "Player " + getPlayerFromDivinity(winner.getWinner()) + " has won");
    }

    private static void playTurn(Player player) throws PlayerQuitException, LossException {

        boolean performedAction = false;
        RequestedAction requestedAction;

        do {
            requestedAction = view.performAction(player);
            if (requestedAction.getAction() == Action.QUIT)
                throw new PlayerQuitException(player);
            performedAction = turnTick.handleTurn(player, requestedAction);
            if (performedAction) {

                //TODO cosa è meglio
                view.update(board.getChangedSquares());
                view.notify(player, "Action Successful");
            }
            else
                view.notify(player, "Action Not Successful");

        } while(!(requestedAction.getAction() == Action.ENDTURN && performedAction) && winner.getWinner() == null);
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
                chosenDivinities = view.getDivinities(players.get(0),nPlayers);
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
                    currentDivinity = view.chooseDivinity(current, chosenDivinities);
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
            coordinates = view.getCoordinates(player);

            if(player.getDivinity().placeWorker(player.getWorker(i),coordinates))
            {
                player.getWorker(i).setCoordinates(coordinates);
                performedAction = true;
            }
            else
                view.notify(player,"The cell is invalid");
        }
    }

    private static void chooseColour(Player player) throws IOException {
        String chosenColour;
        do {
            chosenColour = view.chooseColour(player, colours);
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

    private static List<Player> listDeepCopy(List<Player> toCopy)
    {
        List<Player> newList = new ArrayList<>();
        for(Player player : toCopy)
        {
            newList.add(player.clone());
        }
        return newList;
    }
}

/*creare nuova classe Match che inizializza tutto, qua ci saranno solo
le prime due funz, poi una volta trovati i client faccio partire nuovo thread
View sarà da spostare in Match*/