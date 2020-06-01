package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.server.controller.GameDirector;

public class Lobby {
    ClientList clientList;
    int lobbySize = 0;

    //TODO handle same username
    public void addClient(ClientHandler client) {
        System.out.println("lobby.addClient : " + client.getPlayer().getUsername());
        clientList.add(client);
    }

    public boolean isFull() {
        return lobbySize == clientList.size();
    }

    public void initialize(ClientHandler firstClient) {
        //add first client
        clientList = new ClientList();
        clientList.add(firstClient);
        //ask first client for number of players
        lobbySize = firstClient.getNumberOfPlayers();
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
