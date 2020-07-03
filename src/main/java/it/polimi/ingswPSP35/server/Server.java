package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.Exceptions.DisconnectedException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int SOCKET_PORT = 7777;
    private static ServerSocket socket;

    public static void main(String[] args) {
        try {
            socket = new ServerSocket(SOCKET_PORT);
            //create a new lobby and fill it
            //NB: for multiple simultaneous matches use Executor instead of Thread
            Thread lobbyFiller = new Thread(new LobbyFiller(new Lobby()));
            lobbyFiller.start();
        }
        catch (IOException e) {
            System.exit(1);
        }
    }

    private static class LobbyFiller implements Runnable {
        private final Lobby lobby;

        LobbyFiller(Lobby lobby) {
            this.lobby = lobby;
        }

        @Override
        public void run() {
            //add first client
            System.out.println("Created lobby");
            try {
                lobby.initialize(getClient());
                System.out.println("Added first client");
                //fill lobby
                while (!lobby.isFull()) {
                    ClientHandler newClient = getClient();
                    if (newClient != null && lobby.addClient(newClient))
                        System.out.println("Added client with username: " + newClient.getUsername());
                }
                //start lobby
                lobby.startLobby();
            }
            catch (DisconnectedException disconnectedException) {
                System.out.println("First player disconnected. Shutting down server");
                System.exit(0);
            }
        }

        public ClientHandler getClient() {
            try {
                Socket client = socket.accept();
                return new ClientHandler(client);
            }
            catch (IOException e) {
                System.out.println("Connection dropped");
            }
            return null;
        }
    }
}
