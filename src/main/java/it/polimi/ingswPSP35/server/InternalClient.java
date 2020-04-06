/**
 * Handles every information about client
 */
package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.server.controller.Divinity;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Worker;

import java.io.IOException;

public class InternalClient
{
    ClientConnection connection;
    String player;

    public InternalClient(ClientConnection clientConnection, String player)
    {
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
     * Waits for client to send information and returns it to caller
     * @return String sent by client
     * @throws IOException Error receiving data
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
        return player;
    }

    /*public Worker getWorkerM()
    {
        return player.getWorkerM();
    }
    public Worker getWorkerF()
    {
        return player.getWorkerF();
    }
    public void assignDivinity(Divinity divinity)
    {
        player.setDivinity(divinity);
    }
*/

}
