package it.polimi.ingswPSP35.server;



import it.polimi.ingswPSP35.server.Exceptions.PlayerQuitException;
import it.polimi.ingswPSP35.server.VView.ReducedClasses.ReducedPlayer;
import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO dividere in metodi e gestire disconnessioni
public class Server {

    private static View view = new View();
    private static Controller controller;
    private static List<Match> match;
    private static int port = 7777;

    public static void main(String[] args) {

        do {

            try {
                List<ReducedPlayer> players = null;
                players = view.getPlayers(port);

                match.add(new Match(players, view)); //TODO copia delle liste
                match.get(match.size() - 1).startMatch();
            }
            /*catch (ClientDisconnectedException e)
            {
                view.notify(players, "Player " + e.getDisconnectedPlayer().getUsername() + " disconnected");
            }*/
            catch (PlayerQuitException e) {
                view.notify(e.getPlayer().getUsername() + " quit.");
            }
            finally {
                view.notify("TERMINATEMATCH");
            }
        } while(true);
    }




    /*private static void initializeVariables()
    {
        view = new View();
        controller = new Controller();
        match = null;

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

    }*/
}

/*creare nuova classe Match che inizializza tutto, qua ci saranno solo
le prime due funz, poi una volta trovati i client faccio partire nuovo thread
View sarà da spostare in Match*/