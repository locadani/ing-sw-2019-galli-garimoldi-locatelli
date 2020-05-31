package it.polimi.ingswPSP35.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
