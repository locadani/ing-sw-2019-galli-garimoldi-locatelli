package it.polimi.ingswPSP35.client;

import java.io.IOException;

public class MessagesHandler implements Runnable {
    private String[][] board;
    private UInterface uInterface;
    private ClientConnection clientConnection;
    private Thread action;

    public MessagesHandler(String[][] board, UInterface uInterface, ClientConnection clientConnection) {
        this.board = board;
        this.uInterface = uInterface;
        this.clientConnection = clientConnection;
    }

    @Override
    public void run() {
        String message;

        try {
            while (!Thread.interrupted()) {
                message = clientConnection.receive();
                if (message.equals("PING"))
                    clientConnection.send("PONG");
                else
                    action = new Thread(new UserAction(message, clientConnection, uInterface, board));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
