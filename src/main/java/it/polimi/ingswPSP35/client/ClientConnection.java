/**
 * Handles every information about how to connect to client
 */
package it.polimi.ingswPSP35.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection
{
    ObjectInputStream is;
    ObjectOutputStream os;
    Socket s;

    public ClientConnection(ObjectInputStream is, ObjectOutputStream os, Socket s)
    {
        this.is = is;
        this.os = os;
        this.s = s;
        try {
            s.setSoTimeout(0);
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
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

    public void send(String message) throws IOException {
        os.writeObject(message);
    }

    public String receive() throws IOException, ClassNotFoundException {
        return (String) is.readObject();
    }
}