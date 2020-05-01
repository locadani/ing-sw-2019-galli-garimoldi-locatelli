package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.Exceptions.WinException;
import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.TurnTick;
import it.polimi.ingswPSP35.server.model.*;

import java.util.Iterator;
import java.util.List;


public class Server {

    public static void main(String[] args) {

        //TODO dove mettere le variabili
        //Initialize variables
        String message;
        TurnTick turnTick = null;
        Board board = new Board();
        int nPlayers;
        List<Player> players;
        Player winner = null;
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
        List<String> chosenDivinities = null;
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


        //choose other divinites
        performedAction = false;
        Iterator<Player> playerIterator = players.listIterator(1);
        current = players.get(1);
        String currentDivinity = null;
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
            current = playerIterator.next();
        }
        players.get(0).setDivinity(DivinityFactory.create(chosenDivinities.get(0)));
        message = "Chosen divinites:\n";
        for(Player p: players)
        {
            message = message + p.getUsername() + " chose " + p.getDivinity().getName()+ "\n";
        }
        String finalMessage = message;
        players.forEach(p -> View.notify(p, finalMessage));


        //TODO need to create new instances of Worker class for each player, as they do not exist yet
        //place Workers
        Square chosenSquare = null;
        Coordinates coordinates = null;
        for (Player player : players) {
            performedAction = false;
            while(!performedAction) {
                coordinates = View.getCoordinates(player);
                chosenSquare = board.getSquare(coordinates.getX(),coordinates.getY());
                if(chosenSquare.isFree())
                {
                    chosenSquare.insert(player.getWorker(0));
                    player.getWorker(0).setX(coordinates.getX());
                    player.getWorker(0).setY(coordinates.getY());
                    performedAction = true;
                }
                else
                    View.notify(player,"The cell is occupied");
                //TODO da modificare con metodi giusti
                //chiedere alla divinita se va bene (Es. Bia)
            }
            performedAction = false;
            while(!performedAction)
            {
                coordinates = View.getCoordinates(player);
                chosenSquare = board.getSquare(coordinates.getX(),coordinates.getY());
                if(chosenSquare.isFree())
                {
                    chosenSquare.insert(player.getWorker(1));
                    player.getWorker(1).setX(coordinates.getX());
                    player.getWorker(1).setY(coordinates.getY());
                    performedAction = true;
                }
                else
                    View.notify(player,"The cell is occupied");
            }
            View.notify(player, "COMPLETEDSETUP");
        }


        //Game starts
        turnTick = new TurnTick();
        playerIterator = players.iterator();

        while(winner==null)
        {
            current = playerIterator.next();
            try {
                turnTick.handleTurn(current);
                if (!playerIterator.hasNext())
                    playerIterator = players.iterator();
            }
            catch(WinException e)
            {
                winner = e.getWinner();
            }
        }
    }
}