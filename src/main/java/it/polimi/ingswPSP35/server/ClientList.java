package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.server.model.Player;

import java.util.ArrayList;

public class ClientList extends ArrayList<ClientHandler> {
    public ClientHandler getClientFromPlayer(Player p) {
        return this.stream()
                .filter(clientHandler -> clientHandler.getUsername().equals(p.getUsername()))
                .findAny()
                .orElse(null);
    }
}
