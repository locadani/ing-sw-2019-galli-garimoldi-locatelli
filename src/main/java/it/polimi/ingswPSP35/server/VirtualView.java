package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.model.Coordinates;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VirtualView {
    ClientList clientList;
    ClientHandler currentClient;

    static List<String> allDivinities = List.of(
            "Apollo",
            "Athena",
            "Artemis",
            "Atlas",
            "Demeter",
            "Hephaestus",
            "Minotaur",
            "Pan",
            "Prometheus");

    public VirtualView(ClientList clientList) {
        this.clientList = clientList;
    }

    public List<Player> getPlayerList() {
        List<Player> playerList = new ArrayList<Player>();
        for (ClientHandler clientHandler : clientList) {
            playerList.add(clientHandler.getPlayer());
        }
        return playerList;
    }

    public void broadcast(MessageID messageID, Object object) {
        for (ClientHandler client : clientList)
        {
            client.sendObjectToClient(messageID, object);
        }
    }

    public void sendToPlayer(Player player, MessageID messageID, Object object) {
        ClientHandler client = clientList.getClientFromPlayer(player);
        client.sendObjectToClient(messageID, object);
    }

    public void sendNotificationToPlayer(Player player, String notification) {
        ClientHandler client = clientList.getClientFromPlayer(player);
        client.sendNotificationToClient(notification);
    }

    public Object getAnswer(Player player) {
        ClientHandler client = clientList.getClientFromPlayer(player);
        return client.getClientInput();
    }

}
