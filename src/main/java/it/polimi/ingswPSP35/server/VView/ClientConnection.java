/**
 * Handles every information about how to connect to client
 */
package it.polimi.ingswPSP35.server.VView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection
{
    private final ObjectInputStream is;
    private final ObjectOutputStream os;
    private final Socket s;

    public ClientConnection(ObjectInputStream is, ObjectOutputStream os, Socket s)
    {
        this.is = is;
        this.os = os;
        this.s = s;
    }

    /**
     * Return output stream
     * @return Return output stream
     */
    public ObjectOutputStream getOs()
    {
        return os;
    }

    /**
     * Return input stream
     * @return Return input stream
     */
    public ObjectInputStream getIs()
    {
        return is;
    }

    /**
     * Return socket
     * @return Socket Returns socket
     */
    public Socket getSocket()
    {
        return s;
    }
}

