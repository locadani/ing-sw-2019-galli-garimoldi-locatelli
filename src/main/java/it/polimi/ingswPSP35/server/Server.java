package it.polimi.ingswPSP35.server;



import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.Match;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private static List<Player> players;
    private static View view;
    private static Match match;

    public static void main(String[] args) {

        view = new View();
        players = new ArrayList<>();
        retrievePlayers();
        match = new Match(view,players);
        match.start();
    }


    private static void retrievePlayers()
    {
        //Ask VView NPlayers and Players info
        System.out.println("Waiting for players");
        players = view.getPlayers();
        System.out.println("Received players");
    }

}

/*creare nuova classe Match che inizializza tutto, qua ci saranno solo
le prime due funz, poi una volta trovati i client faccio partire nuovo thread
View sarà da spostare in Match*/