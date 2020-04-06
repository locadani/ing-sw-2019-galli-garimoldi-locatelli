/**
 * Handles first client connection to server
 */
package it.polimi.ingswPSP35.server;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ClientHandler implements Runnable
{
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
                System.out.println("Entro do");
                connection.getOs().writeObject("Enter name: ");
                //retrieve data from client about player and adds to list
                player = (String) connection.getIs().readObject();
                System.out.println("Ricevo nome " + player);
                c = new InternalClient(connection, player);
                System.out.println("Provo aggiungo " + player);
                added = add(c);
            }while(!added);
            connection.getOs().writeObject("All infos are saved");
        }
        catch (Exception e)
        {
            System.out.println("Error retrieving player info");
        }
    }
    private synchronized boolean add(InternalClient player)
    {
        System.out.printf("Sync");
        for (InternalClient e : players) {
            System.out.println("Controllo: " + e.getPlayerName() + " e (da aggiungere->) " +player.getPlayerName());
            if(e.getPlayerName().equals(player.getPlayerName()))
                return false;
        }
        System.out.println("Aggiungo " + player.getPlayerName());
        players.add(player);
        return true;
    }
}