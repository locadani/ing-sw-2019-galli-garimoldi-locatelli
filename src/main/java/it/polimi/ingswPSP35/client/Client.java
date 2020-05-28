package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;
import it.polimi.ingswPSP35.client.gui.Gui;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Client {

    private static Board board = new Board();
    private static Gson gson = new Gson();
    private static ServerHandler serverHandler;
    private static Queue<String> requests = new ConcurrentLinkedQueue<String>();
    private static Scanner in;
    private static UInterface userInterface;

    public static void main(String[] args) {

        in = new Scanner(System.in);

        begin();

        Thread messages = new Thread(serverHandler);
        messages.start();
        //TODO notifica inizio partita
    }

    private static void begin() {
        int UI = 0;
        System.out.println("1 -> Cli\n2 -> Gui");
        UI = in.nextInt();
        in.nextLine();
        serverHandler = new ServerHandler();
        serverHandler.initializeConnection("127.0.0.1",7777);
        if (UI == 1)
            userInterface = new Cli(serverHandler, board);
        else
            userInterface = new Gui(serverHandler, board);

        //userInterface.getConnectionInfo();
        serverHandler.setupInterface(userInterface);
    }
}