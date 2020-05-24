package it.polimi.ingswPSP35.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerReader implements Runnable {
    private final ObjectInputStream input;
    private final LinkedBlockingQueue<Object> inboundMessages;
    private static final String PING = "";

    public ServerReader(ObjectInputStream input, LinkedBlockingQueue<Object> queue) {
        this.input = input;
        this.inboundMessages = queue;
    }

    public void run() {
        while (true) {
            try {
                 Object message = input.readObject();
                if (!message.equals(PING))
                    inboundMessages.add(message);
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
