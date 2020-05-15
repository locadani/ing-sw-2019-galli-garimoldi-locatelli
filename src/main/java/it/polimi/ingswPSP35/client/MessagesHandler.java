package it.polimi.ingswPSP35.client;

import java.io.IOException;
import java.net.SocketTimeoutException;

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
                System.out.println("Entrato");
                message = clientConnection.receive();
                System.out.println(message);
                if (!message.equals("PING"))
                    action = new Thread(new UserAction(message, board, uInterface, clientConnection));
                //TODO join?
            }
        }
        catch (SocketTimeoutException e)
        {
            System.out.println("ecc");
        }
        catch (IOException e) {
            System.out.println("ecc1");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ecc2");
            e.printStackTrace();
        }
    }
}
