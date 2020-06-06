package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.Exceptions.DisconnectedException;
import it.polimi.ingswPSP35.server.controller.GameDirector;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.stream.Collectors;

public class Lobby {
    ClientList clientList;
    int lobbySize = 0;

    //TODO handle same username
    public boolean addClient(ClientHandler client) {
        //check if username is a duplicate of an already added user
        if(clientList.stream()
        .map(ClientHandler::getPlayer)
        .map(Player::getUsername)
        .noneMatch(username -> username.equals(client.getPlayer().getUsername()))) {
            clientList.add(client);
            return true;
        }
        else {
            client.sendNotificationToClient("Username already chosen!\nPlease choose a different username.");
            client.disconnect();
            return false;
        }
    }

    public boolean isFull() {
        return lobbySize == clientList.size();
    }

    public void initialize(ClientHandler firstClient) {
        //add first client
        clientList = new ClientList();
        clientList.add(firstClient);
        //ask first client for number of players
        try {
            lobbySize = firstClient.getNumberOfPlayers();
        } catch (DisconnectedException e) {
            //TODO handle disconnection
        }
        //verify input
        if (lobbySize != 2 && lobbySize != 3) {
            throw new IllegalArgumentException("number of players not supported");
        }
    }

    public void startLobby() {
        GameDirector gameDirector = new GameDirector(new VirtualView(clientList));
        gameDirector.setup();
        gameDirector.playGame();
    }
}
