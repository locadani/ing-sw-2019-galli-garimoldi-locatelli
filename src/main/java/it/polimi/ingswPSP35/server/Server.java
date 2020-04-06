package it.polimi.ingswPSP35.server;


import it.polimi.ingswPSP35.server.controller.BoardHandler;
import it.polimi.ingswPSP35.server.controller.Divinity;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {

    public static void main(String[] args) {
        int SOCKET_PORT = 7777;
        NumberOfPlayers numberOfPlayers;
        numberOfPlayers = new NumberOfPlayers(100);
        int nPlayers;
        List<InternalClient> player = new ArrayList<InternalClient>();
        BoardHandler board;
        List<Divinity> chosenDivinities = new ArrayList<>();
        ServerSocket socket;
        InternalClient winner = null;
        Socket client = null;
        Thread control= new Thread(new ListController(player));
        control.start();

        Thread getClients = new Thread(new NewClientHandler(player,numberOfPlayers));
        getClients.start();

        try {
            while(player.size()<numberOfPlayers.getNumberOfPlayers())
            {
                System.out.println("While: " + player.size() + " NPlayers: " + numberOfPlayers.getNumberOfPlayers());
                Thread.sleep(2000);
            }
            System.out.println("Uscito");
            getClients.interrupt();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        try {
            if (player.size() != numberOfPlayers.getNumberOfPlayers())
                throw new AbortMatchException("Did not reach correct number of players");

            player.forEach(e -> System.out.println("Nome giocatore: " + e.getPlayerName()));
            player.forEach(e -> {
                try {
                    e.send("Playing");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        catch (AbortMatchException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
     /*
        //initialize game
        player.sort(new OrderByIncreasingAge());
        InternalClient current = null;
        Square chosenSquare = null;

        //place workers
        for(int i=0; i<player.size();i++) {
            current = player.get(i);
            current.send("Place Workers");
            chosenSquare = current.receive();
            board.build(chosenSquare, current.getWorkerM());

            chosenSquare = current.receive();
            board.build(chosenSquare, current.getWorkerF());
        }

        //choose divinities
        current = player.get(0);
        current.send("Choose match divinities");
        for(int i=0; i<nPlayers;i++)
        {
            current = player.get(i);
            current.send("Divinity " + i);
            chosenDivinities.add(current.receive());
        }

        //assign divinities
        for(int i=0; i<nPlayers;i++)
        {
            current = player.get(i);
            current.send("Choose among these divinities");
            current.assignDivinity(current.receive());
        }

        Iterator<InternalClient> iterator = player.listIterator();
        while(winner == null)
        {

            if(iterator.hasNext())
                current = iterator.next();
            else
                iterator = player.listIterator();
        }*/
    }
}