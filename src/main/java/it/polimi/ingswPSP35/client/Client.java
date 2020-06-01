package it.polimi.ingswPSP35.client;


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
        userInterface = new Cli(networkHandler);
        networkHandler.connect(userInterface.getConnectionInfo(), userInterface.getPlayerInfo(), userInterface);

    }
}
