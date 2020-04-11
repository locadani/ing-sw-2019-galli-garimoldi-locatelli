/**
 * Handles first client connection to server
 */
package it.polimi.ingswPSP35.server.VView;

import it.polimi.ingswPSP35.Exceptions.ReachedMaxPlayersException;

import java.io.IOException;
import java.util.List;

public class ClientHandler implements Runnable
{
    private boolean add = true;
    private ClientConnection connection;
    private String player;
    private List<InternalClient> players;

    public ClientHandler(ClientConnection t, List<InternalClient> list)
    {
        players = list;
        connection = t;
    }

    /**
     * Retrieves every information needed from player
     * @return Complete object containing info about player and how to connect to it
     */
    public void run() {
        InternalClient c=null;
        boolean added = false;
        try {
            String name;
            do {
                connection.getOs().writeObject("Enter name: ");
                //retrieve data from client about player and adds to list
                player = (String) connection.getIs().readObject();
                c = new InternalClient(connection, player);
                added = add(c);

            }while(!added);
            connection.getOs().writeObject("All infos are saved");
        }
        catch (ReachedMaxPlayersException e)
        {
            try {
                connection.getOs().writeObject("No more places available");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (Exception e)
        {
            System.out.println("Error retrieving player info");
        }

    }

    private synchronized boolean add(InternalClient player) throws ReachedMaxPlayersException
    {
        if(Thread.currentThread().isInterrupted())
        {
            System.out.println("Interrotto");
        }
        else
            System.out.println("Non interrotto");
        if(!Thread.currentThread().isInterrupted()) {
            for (InternalClient e : players) {
                if (e.getPlayerName().equals(player.getPlayerName()))
                    return false;
            }
            players.add(player);
            return true;
        }
        else
            throw new ReachedMaxPlayersException("No more places available");
    }
}