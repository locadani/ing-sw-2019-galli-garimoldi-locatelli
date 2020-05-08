package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.Exceptions.PlayerQuitException;
import it.polimi.ingswPSP35.Exceptions.WinException;
import it.polimi.ingswPSP35.client.AnsiCode;
import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.*;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO dividere in metodi e gestire disconnessioni
public class Server {

    private static String message;
    private static TurnTick turnTick = null;
    private static Board board;
    private static int nPlayers;
    private static List<Player> players;
    private static List<String> chosenDivinities = null;
    private static String currentDivinity;
    private static DivinityMediator divinityMediator;
    private static List<String> colors = new ArrayList<>(List.of("RED", "BLUE", "GREEN"));


    public static void main(String[] args) {

        board = new Board();
        //Ask VView NPlayers and Players info
        System.out.println("Waiting for players");
        players = View.getPlayers();
        nPlayers = players.size();
        System.out.println("Received players");

        //settings
        players.sort(new OrderByIncreasingAge());
        Player current = null;
        boolean performedAction;


        //assign divinities
        performedAction = false;
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


        //TODO iterator
        //choose other divinites
        performedAction = false;
        Iterator<Player> playerIterator = players.listIterator(1);
        current = players.get(1);

        while(chosenDivinities.size()>1)
        {
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
            current = playerIterator.next();
        }
        players.get(0).setDivinity(DivinityFactory.create(chosenDivinities.get(0)));
        players.get(0).getDivinity().setBoard(board);

        message = "Chosen divinites:\n";
        for(Player p: players)
        {
            message = message + p.getUsername() + " chose " + p.getDivinity().getName()+ "\n";
        }
        View.notify(players, message);

        divinityMediator = new DivinityMediator();
        for(Player player : players)
        {
            divinityMediator = player.getDivinity().decorate(divinityMediator);
        }

        Winner winner = new Winner();
        divinityMediator = new SentinelDecorator(divinityMediator);
        for(Player player : players)
        {
            player.getDivinity().setDivinityMediator(divinityMediator);
            player.getDivinity().setWinner(winner);
        }

        //TODO need to create new instances of Worker class for each player, as they do not exist yet
        //place Workers
        Square chosenSquare = null;
        String chosenColor;
        Coordinates coordinates = null;
        for (Player player : players) {
            //TODO controllare che ci sia il colore
            chosenColor = View.chooseColor(player,colors);
            player.setColour(colors.indexOf(chosenColor));
            colors.remove(chosenColor);
            performedAction = false;
            while(!performedAction) {
                coordinates = View.getCoordinates(player);

                if(player.getDivinity().placeWorker(player.getWorker(0),coordinates))
                {
                    player.getWorker(0).setCoordinates(coordinates);
                    performedAction = true;
                }
                else
                    View.notify(player,"The cell is invalid");
            }
            performedAction = false;
            while(!performedAction)
            {
                coordinates = View.getCoordinates(player);

                if(player.getDivinity().placeWorker(player.getWorker(1),coordinates))
                {
                    player.getWorker(1).setCoordinates(coordinates);
                    performedAction = true;
                }
                else
                    View.notify(player,"The cell is invalid");
            }
            View.notify(player, "COMPLETEDSETUP");
        }


        //Game starts
        turnTick = new TurnTick(winner);
        playerIterator = players.iterator();

        try {
            while (winner.getWinner() == null) {
                current = playerIterator.next();
                turnTick.handleTurn(current);
                if (!playerIterator.hasNext())
                    playerIterator = players.iterator();

            }

            View.notify(players, "Player " + getPlayerFromDivinity(winner.getWinner()) + " has won");
        }
        catch (PlayerQuitException e)
        {
            players.remove(e.getPlayer());
            View.removePlayer(e.getPlayer());
            View.notify(players,"Player " + e.getPlayer().getUsername() + " left the game");
            View.notify(players,"TERMINATEMATCH");
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