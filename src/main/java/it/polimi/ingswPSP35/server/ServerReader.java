package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.commons.MessageID;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerReader implements Runnable {
    private final InputStream input;
    private final LinkedBlockingQueue<Object> inboundMessages;
    private static final String PING = "";

    public ServerReader(InputStream input, LinkedBlockingQueue<Object> queue) {
        this.input = input;
        this.inboundMessages = queue;
    }

    public void run() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(input);

            while (true) {
                Object message = objectInput.readObject();
                if (!message.equals(PING))
                    inboundMessages.add(message);
            }
        } catch (SocketException e) {
            try {
                input.close();
            } catch (IOException ioException) {
                System.out.println("Error while attempting to close input stream");
            }
            System.out.println("Socket closed");
        } catch (SocketTimeoutException e) {
            inboundMessages.add(MessageID.DISCONNECTED);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
