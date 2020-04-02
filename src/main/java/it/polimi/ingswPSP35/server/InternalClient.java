//contains every information about client

package it.polimi.ingswPSP35.server;

import java.io.IOException;

public class InternalClient
{
    ClientConnection connection;
    String player;

    public InternalClient(ClientConnection clientConnection, String player)
    {
        //TODO basta fare l'uguale tra parametro e attributo privato? Direi di no
        this.player = player;
        connection = clientConnection;
    }

    /**
     * Sends message to connected socket
     * @param message Message to send
     * @throws IOException something went wrong
     */
    public void send(String message) throws IOException
    {
        connection.getOs().writeObject(message);
    }

    /**
     * Returns player's name
     * @return String containing player's name
     */
    public String getPlayerName()
    {
        return player;
    }
}
