/**
 * Virtual view
 * Works as bridge between Controller and View
 */

package it.polimi.ingswPSP35.server.VView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingswPSP35.server.ClientsPinger;
import it.polimi.ingswPSP35.server.Exceptions.DisconnectedException;
import it.polimi.ingswPSP35.server.VView.ReducedClasses.ReducedPlayer;
import it.polimi.ingswPSP35.server.controller.RequestedAction;
import it.polimi.ingswPSP35.server.controller.NumberOfPlayers;
import it.polimi.ingswPSP35.server.model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;

public class View {

    private Gson gson = new Gson();
    private List<InternalClient> players = new ArrayList<>();
    private NumberOfPlayers numberOfPlayers = new NumberOfPlayers(100);
    private final String completedAction = "SUCCESSFUL";
    private Thread connectionsChecker;
    private ClientsPinger clientsPinger;

    /**
     * Retrieves connections info to contact player
     * @param player player to contact
     * @return Class containing player connection info
     */
    private InternalClient getClient(String player) {
        Iterator<InternalClient> iterator = players.iterator();
        InternalClient client;
        boolean found = false;
        do {
            client = iterator.next();
            if (client.getPlayerName().equals(player))
                found = true;
        } while (!found && iterator.hasNext());
        if (!found)
            client = null;
        return client;
    }

    /**
     * Contacts clients and asks for players' information
     * @return Every client connected and his infos
     */
    public List<Player> getPlayers() {

        clientsPinger = new ClientsPinger();
        connectionsChecker = new Thread(clientsPinger);
        connectionsChecker.start();
        List<Player> playersList = new ArrayList<>();
        Thread getClients = new Thread(new PlayerListRetriever(players, numberOfPlayers, clientsPinger));
        getClients.start();
        while (players.size() < numberOfPlayers.getNumberOfPlayers()) {
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        getClients.interrupt();
        players.forEach(p -> playersList.add(p.getPlayer().toPlayer()));
        return new ArrayList<>(playersList);
    }

    public String chooseColour(Player player, List<String> availableColors) throws DisconnectedException {
        String toSend = "CHOOSECOLOUR";
        String chosenColor = null;
        for (String color : availableColors) {
            toSend = toSend + ":" + color;
        }
        InternalClient client;
        boolean isInvalid = false;
        client = getClient(player.getUsername());
        chosenColor = client.request(toSend);
        return chosenColor;
    }

    /**
     * Asks client to place his workers (Male then Female)
     * @param player who to ask to place Workers
     * @return Positions where player would like to place workers
     */
    public Coordinates getCoordinates(Player player) throws DisconnectedException {
        String toSend = "PLACEWORKER";
        InternalClient client;
        boolean isInvalid = false;
        int cell = 0;
        do {
            client = getClient(player.getUsername());
            cell = Integer.parseInt(client.request(toSend));
            isInvalid = cellIsInvalid(cell);

        } while (isInvalid);
        return new Coordinates(cell);
    }

    /**
     * Asks the first player to choose divinities for current match
     * @param player      player to ask infos
     * @param nDivinities number of divinities required
     * @return Chosen divinites
     */
    public List<String> getDivinities(Player player, int nDivinities) throws DisconnectedException {
        String divinities = null;
        List<String> divinitiesList = null;
        do {
            InternalClient client = getClient(player.getUsername());
            divinities = client.request("GETNDIVINITIES:" + nDivinities);
            Type collectionType = new TypeToken<Collection<String>>() {
            }.getType();
            divinitiesList = gson.fromJson(divinities, collectionType);
        } while (divinitiesList.size() != nDivinities);
        return divinitiesList;
    }


    /**
     * Asks the player to choose among provided divinites
     * @param player             player who has to choose
     * @param availableDivinites divinities among the player can choose
     * @return chosen divinity
     */
    public String chooseDivinity(Player player, List<String> availableDivinites) throws DisconnectedException {
        String divinities = "";
        String chosenDivinity = null;
        Iterator<String> iterator = availableDivinites.iterator();
        String div;
        do {
            div = iterator.next();
            divinities = divinities + ":" + div;

        } while (iterator.hasNext());
        InternalClient client = getClient(player.getUsername());
        chosenDivinity = client.request("GETDIVINITY" + divinities);
        return chosenDivinity;
    }


    /**
     * Allows player to move/build
     * @param player Player who can perform the action
     * @return Action performed
     */
    public RequestedAction performAction(Player player) throws DisconnectedException {
        RequestedAction chosenAction;
        String received = null;
        String[] params;
        InternalClient client = getClient(player.getUsername());
        received = client.request("PERFORMACTION");
        params = received.split(":");
        chosenAction = new RequestedAction(Integer.parseInt(params[0]), params[1], Integer.parseInt(params[2]));

        return chosenAction;
    }

    public void notify(Player player, String message) throws DisconnectedException {
        InternalClient client = getClient(player.getUsername());
        client.send("NOTIFICATION:" + message);
    }

    public void notify(List<Player> players, String message) throws DisconnectedException {
        for (Player p : players) {
            getClient(p.getUsername()).send("NOTIFICATION:" + message);
        }
    }


    public void update(Square changedSquare) throws DisconnectedException {

        String modification = "UPDATE:" + changedSquare.getR() +
                ":" + changedSquare.getC() +
                ":" + changedSquare.getHeight();
        if (changedSquare.getTop() != null) {
            modification = modification + ":" + changedSquare.getTop().getName();
            if (changedSquare.getTop() instanceof Worker)
                modification = modification + ":" + ((Worker) changedSquare.getTop()).getPlayer().getColour();
         }

        for (InternalClient client : players) {
            client.send(modification);
        }
    }

    public void update(List<Square> changedSquares) throws DisconnectedException {

        for (Square changedSquare : changedSquares) {
            update(changedSquare);
        }
    }

    private boolean cellIsInvalid(int cell) {
        if (cell > 0 && cell <= 25)
            return false;
        return true;
    }

    public void removePlayer(Player player) {
        InternalClient client = getClient(player.getUsername());
        client.closeConnection();
        players.remove(client);
    }
}