package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.Exceptions.DisconnectedException;
import it.polimi.ingswPSP35.server.controller.GameDirector;

public class Lobby {
    ClientList clientList;
    int lobbySize = 0;
    VirtualView view;

    public boolean addClient(ClientHandler client) {
        //check if username is a duplicate of an already added user
        if(clientList.stream()
        .map(ClientHandler::getUsername)
        .noneMatch(username -> username.equals(client.getUsername()))) {
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
        try {
            view = new VirtualView(clientList);
            GameDirector gameDirector = new GameDirector(view);
            gameDirector.setup();
            gameDirector.playGame();
        } catch (DisconnectedException e) {
            view.broadcastNotification("User disconnected, terminating game with no winners");
            for (ClientHandler clientHandler : clientList) {
                clientHandler.disconnect();
                clientList.remove(clientHandler);
            }
        }
    }
}
