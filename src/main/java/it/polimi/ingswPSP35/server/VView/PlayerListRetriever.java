/**
 * Main thread waiting for players
 */

package it.polimi.ingswPSP35.server.VView;

import it.polimi.ingswPSP35.server.ClientsPinger;
import it.polimi.ingswPSP35.server.controller.NumberOfPlayers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class PlayerListRetriever implements Runnable {
    private int SOCKET_PORT = 7777;
    private NumberOfPlayers nPlayers;
    private List<InternalClient> player;
    private List<Thread> runningThreads = new ArrayList<>();
    private ServerSocket socket;
    private Socket client = null;
    private ClientsPinger clientsPinger;
    private ExecutorService executor;

    public PlayerListRetriever(List<InternalClient> player, NumberOfPlayers nPlayers, ClientsPinger clientsPinger) {
        this.player = player;
        this.nPlayers = nPlayers;
        this.clientsPinger = clientsPinger;
    }


    /**
     * Main thread to control everything about retrieving players
     */
    @Override
    public void run() {
        try {
            socket = new ServerSocket(SOCKET_PORT);
        }
        catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }
        try {
            int value;
            ClientConnection temporaryConnection = null;
            client = socket.accept();
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            System.out.println("OutstreamCreated");
            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            clientsPinger.ping(output);
            temporaryConnection = new ClientConnection(input, output, client, clientsPinger);
            do {

                value = Integer.parseInt(temporaryConnection.handleRequest("NPLAYERS"));
            } while (isInvalid(value));
            nPlayers.setNumberOfPlayers(value);
            Thread t = new Thread(new PlayerRetriever(temporaryConnection, player, nPlayers));
            runningThreads.add(t);
            t.start();

            while (player.size() < nPlayers.getNumberOfPlayers() && !Thread.currentThread().isInterrupted()) {
                ClientConnection otherPlayerConnection;
                client = socket.accept();
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());
                clientsPinger.ping(output);
                if (player.size() < nPlayers.getNumberOfPlayers()) {
                    otherPlayerConnection = new ClientConnection(input, output, client, clientsPinger);
                    Thread otherPlayers = new Thread(new PlayerRetriever(otherPlayerConnection, player, nPlayers));
                    runningThreads.add(otherPlayers);
                    otherPlayers.start();
                } else {
                    output.writeObject("NOTIFICATION:Reached Max Players");

                }
            }
            blockRunningThreads();
            socket.close();
        }
        catch (Exception e) {
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
        return value != 3 && value != 2;
    }
}