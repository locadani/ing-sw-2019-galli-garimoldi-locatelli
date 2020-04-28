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

        //Initialize variables
        TurnTick turnTick;
        Board board = new Board();
        int nPlayers;
        List<Player> players;
        Player winner = null;
        //Ask VView NPlayers and Players info
        System.out.println("Waiting for players");
        players = View.getPlayers();
        nPlayers = players.size();

        //settings
        players.sort(new OrderByIncreasingAge());
        players.forEach(p ->
                System.out.print("Username: " + p.getUsername() + " Age: " + p.getAge())
        );
        Player current;
        boolean performedAction;


        //assign divinities
        performedAction = false;
        List<String> chosenDivinities = null;
        while(!performedAction)
        {
            try
            {
                chosenDivinities = View.getDivinities(players.get(0), nPlayers);
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
        String currentDivinity;
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


        //TODO need to create new instances of Worker class for each player, as they do not exist yet
        //place Workers
        Square chosenSquare;
        Coordinates coordinates;
        for (Player player : players) {
            while(!performedAction) {
                coordinates = View.getCoordinates(player);
                chosenSquare = board.getSquare(coordinates.getX(),coordinates.getY());
                if(chosenSquare.isFree())
                {
                    chosenSquare.insert(player.getWorker(0));
                    performedAction = true;
                }

                //TODO chiedere alla divinita se va bene (Es. Bia)
            }
            performedAction = false;
            while(!performedAction)
            {
                coordinates = View.getCoordinates(player);
                chosenSquare = board.getSquare(coordinates.getX(),coordinates.getY());
                if(chosenSquare.isFree())
                {
                    chosenSquare.insert(player.getWorker(1));
                    performedAction = true;
                }
            }
        }


        //Game starts
        turnTick = new TurnTick();
        playerIterator = players.iterator();
        current = players.get(0);

        while(winner==null)
        {
            try {
                turnTick.handleTurn(current);
                if (playerIterator.hasNext())
                    current = playerIterator.next();
                else
                    current = players.get(0);
            }
            catch(WinException e)
            {
                winner = e.getWinner();
            }
        }
    }
}