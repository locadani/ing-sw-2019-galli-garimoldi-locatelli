package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.Exceptions.CantPlaceWorkersException;
import it.polimi.ingswPSP35.Exceptions.NoSuchPlayerException;
import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.VView.InternalClient;
import it.polimi.ingswPSP35.server.controller.BoardHandler;
import it.polimi.ingswPSP35.server.controller.Divinity;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Square;

import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.List;


public class Server {

    public static void main(String[] args) {

        //Initialize variables
        Board board = new Board();
        BoardHandler boardHandler = new BoardHandler(board);
        View view = new View();
        int nPlayers;
        List<Player> players = null;
        Player winner = null;
        //Ask VView NPlayers and Players info
        System.out.println("Waiting for players");
        players = View.getPlayers();
        nPlayers = players.size();
        System.out.printf("Received players");
        //players.forEach(p -> System.out.println(p.getPlayerName()));

        //settings
        players.sort(new OrderByIncreasingAge());
        InternalClient current = null;
        boolean performedAction = true;
        List<Square> squares = null;


        //al posto di ritornare lista di Square passo coppia punti

        //place Workers
        for (Player player : players) {
            while(performedAction)
            {
                try
                {
                    squares = View.placeWorkers(player);
                    boardHandler.build(squares.get(0),player.getWorkerM()); //TODO da modificare con metodi giusti
                    boardHandler.build(squares.get(1),player.getWorkerF());
                }
                catch (NoSuchPlayerException e)
                {
                    performedAction = false;
                }
              /*  catch (CantPlaceWorkersException e)
                {
                    performedAction = false;
                }*/
            }
        }

        //al posto di ritornare lista di divinity passo stringa

        //assign divinities
        List<Divinity> chosenDivinities = null
        while(performedAction)
        {
            try
            {
                chosenDivinities = View.getDivinities(players.get(0));
            }
            catch (NoSuchPlayerException e)
            {
                performedAction = false;
            }
        }
        players.get(0).setDivinity(chosenDivinities.get(0));


    }

}