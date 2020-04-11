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
            output.writeObject("Insert number of players: ");
            nPlayers.setNumberOfPlayers(Integer.parseInt((String) input.readObject()));
            temporaryConnection = new ClientConnection(input, output, client);
            Thread  t = new Thread(new ClientHandler(temporaryConnection, player));
            runningThreads.add(t);
            t.start();

            while (player.size()<nPlayers.getNumberOfPlayers()&&!Thread.currentThread().isInterrupted()) {
                ClientConnection otherPlayerConnection;
                System.out.println("Attendo...");
                client = socket.accept();
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());
                otherPlayerConnection = new ClientConnection(input, output, client);
                Thread otherPlayers = new Thread(new ClientHandler(otherPlayerConnection, player));
                runningThreads.add(otherPlayers);
                otherPlayers.start();
            }
            blockRunningThreads();
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
    }

    private void blockRunningThreads()
    {
        for(Thread t : runningThreads)
        {
            if(t.isAlive())
                t.interrupt();
        }
    }
}
