/**
 * Handles first client connection to server
 */
package it.polimi.ingswPSP35.server;

import java.util.concurrent.Callable;

public class ClientHandler implements Callable<InternalClient>
{
    private ClientConnection connection;
    private String player;

    public ClientHandler(ClientConnection t)
    {
        connection = t;
    }

    /**
     * Retrieves every information needed from player
     * @return Complete object containing info about player and how to connect to it
     */
    public InternalClient call() {
        InternalClient c=null;
        try {
            String name,surname;
            connection.getOs().writeObject("Enter name: ");
            //retrieve data from client about player and adds to list
            name = (String) connection.getIs().readObject();
            connection.getOs().writeObject("Enter surname: ");
            //retrieve data from client about player and adds to list
            surname = (String) connection.getIs().readObject();
            player = name + " " + surname;
            c = new InternalClient(connection, player);
            connection.getOs().writeObject("All infos are saved");
        }
        catch (Exception e)
        {
            System.out.println("Error retrieving player info");
        }
        return c;
    }
}
