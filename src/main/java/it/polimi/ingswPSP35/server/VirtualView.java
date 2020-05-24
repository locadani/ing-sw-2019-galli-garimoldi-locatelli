package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.client.Client;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.ArrayList;
import java.util.Comparator;

public class VirtualView {
    ClientList clientList;
    ArrayList<Player> playerList;

    public VirtualView (ClientList clientList) {
        this.clientList = clientList;
    }

    public void setup() {
        playerList = new ArrayList<Player>();
        for(ClientHandler clientHandler : clientList) {
            playerList.add(clientHandler.getPlayer());
        }
        playerList.sort(Comparator.comparing(Player::getAge));

        ClientHandler currentClient = clientList.getClientFromPlayer(playerList.get(0));

    }
}
