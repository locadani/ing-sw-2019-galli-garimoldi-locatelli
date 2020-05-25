package it.polimi.ingswPSP35.server;

public class Lobby {
    ClientList clientList;
    VirtualView view;
    int lobbySize = 0;

    public void addClient(ClientHandler client) {
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
        view = new VirtualView(clientList);
        view.setup();
        view.playGame();
    }
}
