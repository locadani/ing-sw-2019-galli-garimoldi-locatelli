//contains every info about each client connected
//and interacts with itpackage it.polimi.ingswPSP35.server;
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
     * @throws Exception Something went wrong
     */
    public InternalClient call() throws NotCompletedProfileException{
        try {
            String name;
            //retrieve data from client about player and adds to list
            name = (String) connection.getIs().readObject();
            System.out.printf("Dopo getis");
            player = name;
            return new InternalClient(connection, player);
        }
        catch (Exception e)
        {
            throw new NotCompletedProfileException();
        }
    }
}
