package it.polimi.ingswPSP35.client;


import it.polimi.ingswPSP35.client.gui.Gui;

import java.util.Scanner;

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
        System.out.println("Press 1 for gui, 2 for CLI");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals("1"))
            userInterface = new Gui(networkHandler);
        else userInterface = new Cli(networkHandler);
        //networkHandler.connect(userInterface.getConnectionInfo(), userInterface.getPlayerInfo(), userInterface);
        networkHandler.connect("127.0.0.1", userInterface.getPlayerInfo(), userInterface);
    }
}
