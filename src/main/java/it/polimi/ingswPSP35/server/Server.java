package it.polimi.ingswPSP35.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//TODO fare synchronized l'aggiunta di client

public class Server {
    public static void main(String[] args) {
        int SOCKET_PORT = 7777;
        int nPlayers=3; //number of players
        List<InternalClient> player = new ArrayList<InternalClient>();
        //Board board;
        ServerSocket socket;

        //thread to control list content
        Thread thread = new Thread(new ListController(player));
        thread.start();


        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        while (player.size() < nPlayers) {
            try {
                ClientConnection temporaryConnection;
                InternalClient current;

                //wait for socket connection
                Socket client = socket.accept();
                System.out.println("Accettato socket");

                //create connection objects
                ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                temporaryConnection = new ClientConnection(input, output, client);

                //create thread to handle clients
                ExecutorService executor = Executors.newFixedThreadPool(nPlayers);
                Future<InternalClient> future = executor.submit(new ClientHandler(temporaryConnection));

                //get player object to add to list
                current = future.get();
                System.out.println(current);
                player.add(current);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        //end retrieve players
        System.out.println("Giocatore 1: " + player.get(0).getPlayerName());
        System.out.println("Giocatore 2: " + player.get(1).getPlayerName());
        //start game setup

    }
}
