package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.BoardHandler;
import it.polimi.ingswPSP35.server.controller.TurnTick;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.DivinityFactory;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Square;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Server {

    public static void main(String[] args) {

        //Initialize variables
        TurnTick turnTick;
        Board board = new Board();
        BoardHandler boardHandler = new BoardHandler(board);
        View view = new View();
        int nPlayers;
        List<Player> players;
        Player winner = null;
        //Ask VView NPlayers and Players info
        System.out.println("Waiting for players");
        players = View.getPlayers();
        nPlayers = players.size();

        //settings
        players.sort(new OrderByIncreasingAge());
        players.forEach(p -> p.printInfo());
        Player current = null;
        boolean performedAction = false;
        Square square = null;


        //al posto di ritornare lista di Square passo coppia punti

        //place Workers
        for (Player player : players) {
            while(!performedAction) {
                square = View.getSquare(player);
                performedAction = boardHandler.build(square, player.getWorkerM()); //TODO da modificare con metodi giusti
            }
            performedAction = false;
            while(!performedAction)
            {
                square = View.getSquare(player);
                performedAction = boardHandler.build(square,player.getWorkerF());

            }
        }

        //al posto di ritornare lista di divinity passo stringa

        //assign divinities
        performedAction = false;
        List<String> chosenDivinities = null;
        while(!performedAction)
        {
            try
            {
                chosenDivinities = View.getDivinities(players.get(0));
                players.get(0).setDivinity(DivinityFactory.getDivinity(chosenDivinities.get(0)));
                chosenDivinities.remove(0);
                performedAction = true;
            }
            catch (Exception e)
            {
                performedAction = false;
            }
        }


        performedAction = false;
        //choose other divinites
        Iterator<Player> playerIterator = players.listIterator(1);
        current = players.get(1);
        String currentDivinity = null;
        while(playerIterator.hasNext())
        {
            while(!performedAction) {
                try
                {
                    currentDivinity = View.chooseDivinity(current, chosenDivinities);
                    current.setDivinity(DivinityFactory.getDivinity(currentDivinity));
                    chosenDivinities.remove(currentDivinity);
                    performedAction = true;
                }
                catch (Exception e)
                {
                    performedAction = false;
                }
            }
        }

        turnTick = new TurnTick();
        playerIterator = players.iterator();
        current = players.get(0);
        //Game starts
        while(winner==null)
        {
            turnTick.handleTurn(current);
            if(playerIterator.hasNext())
                current = playerIterator.next();
            else
                current = players.get(0);
        }
    }
}