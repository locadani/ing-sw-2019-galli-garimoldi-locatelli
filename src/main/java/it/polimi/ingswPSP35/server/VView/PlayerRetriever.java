/**
 * Retrieves info about every player
 */

package it.polimi.ingswPSP35.server.VView;

//testFile
import java.io.IOException;

import com.google.gson.Gson;

import it.polimi.ingswPSP35.server.Exceptions.DisconnectedException;
import it.polimi.ingswPSP35.server.VView.ReducedClasses.ReducedPlayer;
import it.polimi.ingswPSP35.server.controller.NumberOfPlayers;

import java.net.SocketException;
import java.util.List;

public class PlayerRetriever implements Runnable {
    private final boolean add = true;
    private final ClientConnection connection;
    private ReducedPlayer player;
    private final NumberOfPlayers nPlayers;
    private final List<InternalClient> players;

    public PlayerRetriever(ClientConnection t, List<InternalClient> list, NumberOfPlayers nPlayers) {
        this.nPlayers = nPlayers;
        players = list;
        connection = t;
    }

    /**
     * Retrieves every information needed from player
     * @return Complete object containing info about player and how to connect to it
     */
    public void run() {
        String receivedPlayer = null;
        String[] info;
        Gson gson = new Gson();
        InternalClient c = null;
        int added;
        try {
            String name;

            do {
                try {
                    receivedPlayer = connection.handleRequest("PLAYERINFO");
                }
                catch (DisconnectedException e) {
                    e.printStackTrace();
                }
                info = receivedPlayer.split(":");
                //retrieve data from client about player and adds to list
                player = new ReducedPlayer(info[0], Integer.parseInt(info[1]));
                c = new InternalClient(connection, player);
                added = add(c);

            } while (added > 0);
            if (added == 0)
                connection.getOs().writeObject("NOTIFICATION:All infos are saved");
            else
                connection.getOs().writeObject("NOTIFICATION:No more places available");
        }
        catch (SocketException e) {
            System.out.println("Disconnected client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds parameter to Players
     * @param player Player to add
     * @return true if everything was performed correctly, false otherwise
     */
    private int add(InternalClient player){
        synchronized (players) {
            if (!Thread.currentThread().isInterrupted() && players.size() < nPlayers.getNumberOfPlayers()) {
                for (InternalClient e : players) {
                    if (e.getPlayerName().equals(player.getPlayerName()))
                        return 1;
                }
                players.add(player);
                return 0;
            } else
                return -1;
        }
    }
}