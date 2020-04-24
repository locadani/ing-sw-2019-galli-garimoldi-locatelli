/**
 * Virtual view
 * Works as bridge between Controller and View
 */

package it.polimi.ingswPSP35.server.VView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingswPSP35.Exceptions.NoSuchPlayerException;
import it.polimi.ingswPSP35.server.VView.ReducedClasses.ReducedPlayer;
import it.polimi.ingswPSP35.server.controller.RequestedAction;
import it.polimi.ingswPSP35.server.controller.NumberOfPlayers;
import it.polimi.ingswPSP35.server.model.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class View implements BoardObserver {
    private static Gson gson = new Gson();
    private static List<InternalClient> players = new ArrayList<>();
    private static NumberOfPlayers numberOfPlayers = new NumberOfPlayers(100);

    /**
     * Retrieves connections info to contact player
     * @param player player to contact
     * @return Class containing player connection info
     * @throws NoSuchPlayerException If the player does not exist
     */
    private static InternalClient getClient(ReducedPlayer player) throws  NoSuchPlayerException
    {
        InternalClient client = players.get(0);
        Iterator<InternalClient> iterator = players.iterator();
        boolean found = false;
        while(found == false && iterator.hasNext())
        {
            if(client.equals(player))  //TODO non so so è giusto equals
                found = true;
            client = iterator.next();
        }
        if(!found)
            throw new NoSuchPlayerException();
        return client;
    }

    /**
     * Contacts clients and asks for players' information
     * @return Every client connected and his infos
     */
    public static List<Player> getPlayers()
    {
        List<Player> playersList = new ArrayList<Player>();
        Thread getClients = new Thread(new NewClientHandler(players,numberOfPlayers));
        getClients.start();
        try {
            while(players.size()<numberOfPlayers.getNumberOfPlayers())
            {
                Thread.sleep(2000);
            }
            System.out.println("FOuriwhile");
            getClients.interrupt();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        players.forEach(p -> playersList.add(p.getPlayer().toPlayer()));
        return new ArrayList<Player>(playersList);
    }

    /**
     * Asks client to place his workers (Male then Female)
     * @param player who to ask to place Workers
     * @return Positions where player would like to place workers
     * @throws NoSuchPlayerException If the player does not exist
     */
    public static Cell getCell(Player player)
    {
        String position = null;
        ReducedPlayer rPlayer = new ReducedPlayer(player);
        try {
            InternalClient client = getClient(rPlayer);
            client.send("PLACEWORKER");
            position = client.receive();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Cell(Integer.parseInt(String.valueOf(position.charAt(0))),Integer.parseInt(String.valueOf(position.charAt(1))));
    }

    /**
     * Asks the first player to choose divinities for current match
     * @param player player to ask infos
     * @return Chosen divinites
     */
    public static List<String> getDivinities(Player player)
    {
        String divinities = null;
        ReducedPlayer rPlayer = new ReducedPlayer(player);
        List<String> divinitiesList = null;
        do {
            try {
                InternalClient client = getClient(rPlayer);
                client.send("GETNDIVINITIES");
                divinities = client.receive();
                Type collectionType = new TypeToken<Collection<Integer>>(){}.getType();
                divinitiesList = gson.fromJson(divinities, collectionType);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } while(divinitiesList.size()!=numberOfPlayers.getNumberOfPlayers());
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
        ReducedPlayer rPlayer = new ReducedPlayer(player);
        Iterator<String> iterator = availableDivinites.iterator();
        String div = availableDivinites.get(0);
        while(iterator.hasNext())
        {
            divinities = divinities + div;
            if(iterator.hasNext())
            {
                divinities = divinities + "|";
                div = iterator.next();
            }
        }
        try {
            InternalClient client = getClient(rPlayer);
            client.send("GETDIVINITY");
            client.send(divinities);
            chosenDivinity = client.receive();
        }
        catch (Exception e)
        {
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
        ReducedPlayer rPlayer = new ReducedPlayer(player);
        RequestedAction chosenAction = null;
        String received = null;
        try {
            InternalClient client = getClient(rPlayer);
            client.send("MAKEMOVE");
            received = client.receive();
            chosenAction = gson.fromJson(received, RequestedAction.class);
        }
        catch (Exception e)
        {
            return null;
        }
        return chosenAction;
    }
 /*   public static void chooseDivinity(Player player) throws NoSuchPlayerException
    {
        InternalClient client = getClient(player);
        //Contatta client per chiedere
    }
*/

    public static String serialize(Object o)
    {
        String serialized = null;
        Gson gson = new Gson();
        serialized = gson.toJson(o);
        return serialized;
    }


    @Override
    public void update(Object o) {
        String modification = null;
            players.forEach(p -> {
                try {
                    p.send(modification);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }
}
