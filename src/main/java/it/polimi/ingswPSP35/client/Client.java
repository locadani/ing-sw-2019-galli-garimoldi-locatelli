package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;
import it.polimi.ingswPSP35.client.gui.Gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    private static final String[][] board = new String[5][5];
    private static final Gson gson = new Gson();
    private static String playername;
    private static int clientnumber;
    private static UInterface uInterface;
    private static ClientConnection clientConnection;

    public static void main(String[] args){

        String connectionInfo;
        initializeBoard();
        uInterface = new Cli();
        do {
            connectionInfo = uInterface.getConnectionInfo();

        }while (!connectionSetup(connectionInfo));

        Thread messages = new Thread(new MessagesHandler(board, uInterface, clientConnection));
        messages.start();
        //TODO notifica inizio partita

    }

    private static void initializeBoard()
    {
        for(int i=0; i<25; i++)
            board[i/5][i%5] = "E";
    }

    public static boolean connectionSetup(String connectionInfo) {
        boolean completed;
        Socket socket;
        ObjectOutputStream output;
        ObjectInputStream input;
        String[] info = connectionInfo.split(":");
        try {
            socket = new Socket("127.0.0.1", 7777);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            clientConnection = new ClientConnection(input, output, socket);
            completed = true;
        } catch (IOException e) {
            completed = false;
        }
        return completed;
    }

    private static UInterface chooseUInterface() {
        return new TestFile();
    }
}