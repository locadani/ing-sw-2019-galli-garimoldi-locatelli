package it.polimi.ingswPSP35.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class Server {

    public static void main(String[] args) {
        int SOCKET_PORT = 7777;
        int nPlayers = 2;
        List<InternalClient> player = new ArrayList<InternalClient>();
        //Board board;
        ServerSocket socket;
        InternalClient winner = null;
        Socket client = null;

        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        //attendo primo giocatore
        System.out.println("Waiting first player...");
        try {
            client = socket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Future<InternalClient>> results = new ArrayList<Future<InternalClient>>();
        ExecutorService executor = null;


        try {
            ClientConnection temporaryConnection;
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            output.writeObject("Insert number of players: ");
            nPlayers = Integer.parseInt((String) input.readObject());
            System.out.println(Integer.toString(nPlayers) + " players match created");
            executor = Executors.newCachedThreadPool();
            temporaryConnection = new ClientConnection(input, output, client);
            results.add(executor.submit(new ClientHandler(temporaryConnection)));

            while (results.size() < nPlayers) {
                ClientConnection newPlayers;
                client = socket.accept();
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());
                newPlayers = new ClientConnection(input, output, client);
                results.add(executor.submit(new ClientHandler(newPlayers)));
            }

            socket.close();
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.MINUTES);


            for (int i = 0; i < results.size(); i++) {
                player.add(results.get(i).get());
            }
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        try {
            if (player.size() != nPlayers)
                throw new AbortMatchException("Did not reach correct number of players");

            player.forEach(e -> System.out.println("Nome giocatore: " + e.getPlayerName()));
        }
        catch (AbortMatchException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        //initialize game
/*        player.sort(new OrderByIncreasingAge());
        InternalClient current;

        for(int i=0; i<player.size();i++) {
            current = player.get(i);
            current.send("Place Workers");
            current.receive();
            board.add(worker, cell);
            board.add(worker, cell);
        }
        current = player.get(0);

        ListIterator<InternalClient> iterator = player.listIterator();
        while(winner == null)
        {

            if(iterator.hasNext())
                current = iterator.next();
            else
                iterator = player.listIterator();
        }*/
    }
}
