/**
 * Handles first client connection to server
 */

package it.polimi.ingswPSP35.server.VView;

import com.google.gson.Gson;

import it.polimi.ingswPSP35.Exceptions.ReachedMaxPlayersException;
import it.polimi.ingswPSP35.server.VView.ReducedClasses.ReducedPlayer;
import it.polimi.ingswPSP35.server.controller.NumberOfPlayers;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

public class ClientHandler implements Runnable
{
    private boolean add = true;
    private ClientConnection connection;
    private ReducedPlayer player;
    private NumberOfPlayers nPlayers;
    private List<InternalClient> players;

    public ClientHandler(ClientConnection t, List<InternalClient> list, NumberOfPlayers nPlayers)
    {
        this.nPlayers = nPlayers;
        players = list;
        connection = t;
    }

    /**
     * Retrieves every information needed from player
     * @return Complete object containing info about player and how to connect to it
     * @throws ReachedMaxPlayersException Exception thrown when no more places are available
     */
    public void run() {
        String receivedPlayer;
        String[] info;
        Gson gson = new Gson();
        InternalClient c=null;
        boolean added = false;
        try {
            String name;
            //TODO usare Json object
            //TODO ges
            do {
                connection.getOs().writeObject("PLAYERINFO");
                receivedPlayer = (String)connection.getIs().readObject();
                info = receivedPlayer.split("\\|");
                //retrieve data from client about player and adds to list
                player = new ReducedPlayer(info[0],Integer.parseInt(info[1]));
                c = new InternalClient(connection, player);
                added = add(c);

            } while(!added);
            connection.getOs().writeObject("NOTIFICATION|All infos are saved");
        }
        catch (ReachedMaxPlayersException e)
        {
            try {
                connection.getOs().writeObject("NOTIFICATION|No more places available");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (SocketException e)
        {
            System.out.println("Disconnected client");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Adds parameter to Players
     * @param player Player to add
     * @return true if everything was performed correctly, false otherwise
     * @throws ReachedMaxPlayersException
     */
    //TODO ritorna posti rimanenti
    private boolean add(InternalClient player) throws ReachedMaxPlayersException
    {
        synchronized (players) {
            if (!Thread.currentThread().isInterrupted() && players.size() < nPlayers.getNumberOfPlayers()) {
                for (InternalClient e : players) {
                    if (e.getPlayerName().equals(player.getPlayerName()))
                        return false;
                }
                players.add(player);
                return true;
            } else
                throw new ReachedMaxPlayersException();
        }
    }
}