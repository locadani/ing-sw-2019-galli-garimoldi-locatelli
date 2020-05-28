/**
 * Handles every information about client
 */
package it.polimi.ingswPSP35.server.VView;

import it.polimi.ingswPSP35.Exceptions.DisconnectedException;
import it.polimi.ingswPSP35.server.VView.ReducedClasses.ReducedPlayer;

import java.io.IOException;

public class InternalClient {

    private final ClientConnection connection;
    private final ReducedPlayer player;

    public InternalClient(ClientConnection clientConnection, ReducedPlayer player)
    {
        this.player = player;
        connection = clientConnection;
    }

    public void closeConnection()
    {
        try {
            connection.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientConnection getClientConnection()
    {
        return connection;
    }

    /**
     * Sends message to connected socket
     * @param message Message to send
     * @throws IOException if something went wrong
     */
    public void send(String message) throws DisconnectedException
    {
        try {
            connection.getOs().writeObject(message);
        }catch(IOException e)
        {
            System.out.println("Player Username: " + player.getUsername());
            throw new DisconnectedException(player.getUsername());
        }
    }

    /**
     * Waits for client to send information and returns it to caller
     * @return String sent by client
     * @throws IOException If there was an error receiving data
     */
    public String receive() throws IOException
    {
        String received = null;
        try {
            received = (String) connection.getIs().readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return received;
    }

    /**
     * Returns player's name
     * @return String containing player's name
     */
    public String getPlayerName()
    {
        return player.getUsername();
    }

    public ReducedPlayer getPlayer()
    {
        return player;
    }

    public ClientConnection getConnection() {
        return connection;
    }

    public String request(String message) throws DisconnectedException {
        try {
            return connection.handleRequest(message);
        }catch (IOException e)
        {
            throw new DisconnectedException(player.getUsername());
        }
    }
}
