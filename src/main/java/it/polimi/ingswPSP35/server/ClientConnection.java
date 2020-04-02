//contains every info to talk to client

package it.polimi.ingswPSP35.server;

import java.io.DataInputStream;


import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection
{
    ObjectInputStream is;
    ObjectOutputStream os;
    Socket s;

    public ClientConnection(ObjectInputStream is, ObjectOutputStream os, Socket s)
    {
        //TODO basta fare così o devo fare new?
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
     * @return Socket
     */
    public Socket getSocket()
    {
        return s;
    }
}

