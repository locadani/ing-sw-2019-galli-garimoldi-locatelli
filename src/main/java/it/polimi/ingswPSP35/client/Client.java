package it.polimi.ingswPSP35.client;


import it.polimi.ingswPSP35.client.gui.Gui;

public class Client implements Runnable {
    String username;
    int age;
    UInterface userInterface;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    public void run() {
        NetworkHandler networkHandler = new NetworkHandler();
        userInterface = new Gui(networkHandler);
        //networkHandler.connect(userInterface.getConnectionInfo(), userInterface.getPlayerInfo(), userInterface);
        networkHandler.connect("127.0.0.1", userInterface.getPlayerInfo(), userInterface);
    }
}
