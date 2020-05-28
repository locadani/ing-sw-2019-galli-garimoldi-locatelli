/**
 * Handles every information about how to connect to client
 */
package it.polimi.ingswPSP35.server.VView;

import it.polimi.ingswPSP35.server.ClientsPinger;
import it.polimi.ingswPSP35.Exceptions.DisconnectedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection {
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Socket s;
    private ClientsPinger pinger;

    public ClientConnection(ObjectInputStream is, ObjectOutputStream os, Socket s, ClientsPinger pinger) {
        this.is = is;
        this.os = os;
        this.s = s;
        try {
            s.setSoTimeout(0);
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        this.pinger = pinger;
    }

    /**
     * Return output stream
     * @return Return output stream
     */
    public ObjectOutputStream getOs() {
        return os;
    }

    /**
     * Return input stream
     * @return Return input stream
     */
    public ObjectInputStream getIs() {
        return is;
    }

    /**
     * Return socket
     * @return Socket Returns socket
     */
    public Socket getSocket() {
        return s;
    }

    public void write(String message)
    {
        try {
            os.writeObject(message);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String handleRequest(String request) throws DisconnectedException {
        String receivedMessage = null;
        try {
            os.writeObject(request);
            pinger.pause(os);
            do {
                receivedMessage = (String) is.readObject();
            } while (receivedMessage.equals("PING"));
        }
        catch (IOException e) {
            throw new DisconnectedException();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        pinger.ping(os);
        return receivedMessage;
    }
}