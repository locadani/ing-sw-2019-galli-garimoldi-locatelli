package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.Exceptions.DisconnectedException;
import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.commons.ReducedSquare;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.ArrayList;
import java.util.List;

public class VirtualView {
    ClientList clientList;

    public VirtualView(ClientList clientList) {
        this.clientList = clientList;
    }

    public List<Player> getPlayerList() {
        List<Player> playerList = new ArrayList<Player>();
        for (ClientHandler clientHandler : clientList) {
            playerList.add(clientHandler.createPlayer());
        }
        return playerList;
    }

    public void broadcast(MessageID messageID, Object object) {
        for (ClientHandler client : clientList)
        {
            client.sendObjectToClient(messageID, object);
        }
    }

    public void broadcastNotification(String notification) {
        for (ClientHandler client : clientList)
        {
            client.sendNotificationToClient(notification);
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

    public Object getAnswer(Player player) throws DisconnectedException {
        ClientHandler client = clientList.getClientFromPlayer(player);
        return client.getClientInput();
    }

    public RequestedAction performAction(Player player) throws DisconnectedException {
        ClientHandler client = clientList.getClientFromPlayer(player);
        client.sendObjectToClient(MessageID.PERFORMACTION, null);
        return (RequestedAction) client.getClientInput();
    }

    public void update(List<ReducedSquare> squareList) {
        broadcast(MessageID.UPDATE, squareList);
    }

    public void disconnect(Player player) {
        ClientHandler client = clientList.getClientFromPlayer(player);
        client.disconnect();
        clientList.remove(client);
    }

}
