/**
 * Main thread waiting for players
 */

package it.polimi.ingswPSP35.server.VView;

import it.polimi.ingswPSP35.server.controller.NumberOfPlayers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NewClientHandler implements Runnable {
    int SOCKET_PORT = 7777;
    NumberOfPlayers nPlayers;
    List<InternalClient> player;
    List<Thread> runningThreads= new ArrayList<>();
    ServerSocket socket;
    Socket client = null;

    public NewClientHandler(List<InternalClient> player, NumberOfPlayers nPlayers)
    {
        this.player = player;
        this.nPlayers = nPlayers;
    }


    /**
     * Main thread to control everything about retrieving players
     */
    @Override
    public void run() {
        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }
        try
        {
            ClientConnection temporaryConnection;
            client = socket.accept();
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            output.writeObject("NPLAYERS");
            nPlayers.setNumberOfPlayers(Integer.parseInt((String) input.readObject()));
            temporaryConnection = new ClientConnection(input, output, client);
            Thread  t = new Thread(new ClientHandler(temporaryConnection, player, nPlayers));
            runningThreads.add(t);
            t.start();

            while (player.size()<nPlayers.getNumberOfPlayers()&&!Thread.currentThread().isInterrupted()) {
                ClientConnection otherPlayerConnection;
                client = socket.accept();
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());
                if(player.size()<nPlayers.getNumberOfPlayers())
                {
                    otherPlayerConnection = new ClientConnection(input, output, client);
                    Thread otherPlayers = new Thread(new ClientHandler(otherPlayerConnection, player, nPlayers));
                    runningThreads.add(otherPlayers);
                    otherPlayers.start();
                }
                else {
                    output.writeObject("NOTIFICATION|Reached Max Players");

                }
            }
            blockRunningThreads();
            socket.close();
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
    }

    /**
     * Requests threads to stop
     */
    private void blockRunningThreads()
    {
        for(Thread t : runningThreads)
        {
            if(t.isAlive())
            {
                t.interrupt();
            }
        }
    }
}