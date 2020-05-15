/**
 * Main thread waiting for players
 */

package it.polimi.ingswPSP35.server.VView;

import it.polimi.ingswPSP35.server.Pinger;
import it.polimi.ingswPSP35.server.controller.NumberOfPlayers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PlayerListRetriever implements Runnable {

    private final NumberOfPlayers nPlayers;
    private final List<InternalClient> player;
    private final List<Thread> runningThreads = new ArrayList<>();
    private int SOCKET_PORT;
    private ServerSocket socket;
    private Socket client = null;
    private Pinger pinger;

    public PlayerListRetriever(List<InternalClient> player, int port, NumberOfPlayers nPlayers, Pinger pinger) {
        this.player = player;
        this.SOCKET_PORT = port;
        this.nPlayers = nPlayers;
        this.pinger = pinger;
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
        try {
            int value;
            ClientConnection temporaryConnection;
            client = socket.accept();
            //client.setSoTimeout(3000);
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            pinger.addClient(output);
            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            do {
                output.writeObject("NPLAYERS");
                value = Integer.parseInt((String) input.readObject());
            } while (isInvalid(value));
            nPlayers.setNumberOfPlayers(value);
            temporaryConnection = new ClientConnection(input, output, client);
            Thread t = new Thread(new PlayerRetriever(temporaryConnection, player, nPlayers));
            runningThreads.add(t);
            t.start();

            while (player.size() < nPlayers.getNumberOfPlayers() && !Thread.currentThread().isInterrupted()) {
                ClientConnection otherPlayerConnection;
                client = socket.accept();
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());
                pinger.addClient(output);
                if (player.size() < nPlayers.getNumberOfPlayers()) {
                    otherPlayerConnection = new ClientConnection(input, output, client);
                    Thread otherPlayers = new Thread(new PlayerRetriever(otherPlayerConnection, player, nPlayers));
                    runningThreads.add(otherPlayers);
                    otherPlayers.start();
                } else {
                    output.writeObject("NOTIFICATION:Reached Max Players");

                }
            }
            blockRunningThreads();
            socket.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * Requests threads to stop
     */
    private void blockRunningThreads() {
        for (Thread t : runningThreads) {
            if (t.isAlive()) {
                t.interrupt();
            }
        }
    }

    private boolean isInvalid(int value) {
        if (value > 3 || value < 2) {
        }
        return false;
    }
}