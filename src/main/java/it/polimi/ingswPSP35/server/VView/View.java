/**
 * Virtual view
 * Works as bridge between Controller and View
 */

package it.polimi.ingswPSP35.server.VView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingswPSP35.server.Server;
import it.polimi.ingswPSP35.server.VView.ReducedClasses.ReducedPlayer;
import it.polimi.ingswPSP35.server.controller.CheckConnection;
import it.polimi.ingswPSP35.server.controller.RequestedAction;
import it.polimi.ingswPSP35.server.controller.NumberOfPlayers;
import it.polimi.ingswPSP35.server.model.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class View {

    private static Gson gson = new Gson();
    private static List<InternalClient> players = new ArrayList<>();
    private static NumberOfPlayers numberOfPlayers = new NumberOfPlayers(100);
    private final static String completedAction = "SUCCESSFUL";
    private static Thread connectionsChecker;
    private static ReducedPlayer disconnectedPlayer = null;

    /**
     * Retrieves connections info to contact player
     * @param player player to contact
     * @return Class containing player connection info
     */
    private static InternalClient getClient(String player)
    {
        Iterator<InternalClient> iterator = players.iterator();
        InternalClient client;
        boolean found = false;
        do
        {
            client = iterator.next();
            if(client.getPlayerName().equals(player))
                found = true;
        } while(!found && iterator.hasNext());
        if(!found)
            client = null;
        return client;
    }

    /**
     * Contacts clients and asks for players' information
     * @return Every client connected and his infos
     */
    public static List<Player> getPlayers()
    {
        List<Player> playersList = new ArrayList<>();
        Thread getClients = new Thread(new NewClientHandler(players,numberOfPlayers));
        getClients.start();
        while(players.size()<numberOfPlayers.getNumberOfPlayers())
        {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        getClients.interrupt();
        players.forEach(p -> playersList.add(p.getPlayer().toPlayer()));
        return new ArrayList<>(playersList);
    }

    public static String chooseColour(Player player, List<String> availableColors)
    {
        String toSend="CHOOSECOLOUR";
        String chosenColor = null;
        for(String color : availableColors)
        {
            toSend = toSend + "|" + color;
        }
        InternalClient client;
        boolean isInvalid = false;
        client = getClient(player.getUsername());
        try {
            client.send(toSend);
            chosenColor = client.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chosenColor;
    }

    /**
     * Asks client to place his workers (Male then Female)
     * @param player who to ask to place Workers
     * @return Positions where player would like to place workers
     */
    public static Coordinates getCoordinates(Player player)
    {
        String toSend = "PLACEWORKER";
        InternalClient client;
        boolean isInvalid = false;
        int cell = 0;
        do {
            client = getClient(player.getUsername());
            try {
                client.send(toSend);
                cell = Integer.parseInt(client.receive());
            } catch (IOException e) {
                e.printStackTrace();
            }
            isInvalid = cellIsInvalid(cell);

        } while(isInvalid);
        return new Coordinates(cell);
    }

    /**
     * Asks the first player to choose divinities for current match
     * @param player player to ask infos
     * @param nDivinities number of divinities required
     * @return Chosen divinites
     */
    public static List<String> getDivinities(Player player, int nDivinities)
    {
        String divinities = null;
        List<String> divinitiesList = null;
        do {
            InternalClient client = getClient(player.getUsername());
            try {
                client.send("GETNDIVINITIES|"+nDivinities);
                divinities = client.receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Type collectionType = new TypeToken<Collection<String>>(){}.getType();
            divinitiesList = gson.fromJson(divinities, collectionType);
        } while(divinitiesList.size()!=nDivinities);
        return divinitiesList;
    }


    /**
     * Asks the player to choose among provided divinites
     * @param player player who has to choose
     * @param availableDivinites divinities among the player can choose
     * @return chosen divinity
     */
    public static String chooseDivinity(Player player, List<String> availableDivinites)
    {
        String divinities = "";
        String chosenDivinity = null;
        Iterator<String> iterator = availableDivinites.iterator();
        String div;
        do
        {
            div = iterator.next();
            divinities = divinities + "|" + div;

        }while(iterator.hasNext());
        InternalClient client = getClient(player.getUsername());
        try {
            client.send("GETDIVINITY"+divinities);
            chosenDivinity = client.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chosenDivinity;
    }


    /**
     * Allows player to move/build
     * @param player Player who can perform the action
     * @return Action performed
     */
    public static RequestedAction performAction(Player player)
    {
        RequestedAction chosenAction;
        String received = null;
        String[] params;
        InternalClient client = getClient(player.getUsername());
        try {
            client.send("PERFORMACTION");
            received = client.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        params = received.split("\\|");
        chosenAction = new RequestedAction(Integer.parseInt(params[0]),params[1],Integer.parseInt(params[2]));

        return chosenAction;
    }

    public static void notify(Player player, String message)
    {
        InternalClient client = getClient(player.getUsername());
        try {
            client.send("NOTIFICATION|"+message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void notify(List<Player> players, String message)
    {
        for(Player p : players){
            try {
                getClient(p.getUsername()).send("NOTIFICATION|" + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void update(Square changedSquare){

        String modification = "UPDATE|" +changedSquare.getR()+
                "|" + changedSquare.getC()+
                "|"+changedSquare.getHeight();
        if(changedSquare.getTop()!=null) {
            modification = modification + "|" + changedSquare.getTop().getName();
            if (changedSquare.getTop() instanceof Worker)
                modification = modification + "|" + ((Worker) changedSquare.getTop()).getPlayer().getColour();
        }

        for(InternalClient client : players)
        {
            try {
                client.send(modification);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean cellIsInvalid(int cell)
    {
        if(cell>0 && cell<=25)
            return false;
        return true;
    }

    public static void removePlayer(Player player)
    {
        InternalClient client = getClient(player.getUsername());
        client.closeConnection();
        players.remove(client);
    }

    public static void initializeConnectionsChecker()
    {/*
        Map<String, ClientConnection> connections = new HashMap<>();
        for(InternalClient player : players)
        {
            connections.put(player.getPlayerName(),player.getConnection());
        }
        connections = Collections.synchronizedMap(connections);*/
        connectionsChecker = new Thread(new CheckConnection(players));
        connectionsChecker.start();
    }
}