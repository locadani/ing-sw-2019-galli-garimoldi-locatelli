package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.commons.MessageID;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

public class Reader implements Runnable {
    private final ObjectInputStream input;
    private static final String PING = "";
    LinkedBlockingQueue<String> inboundMessages;

    public Reader(ObjectInputStream input, LinkedBlockingQueue<String> inboundMessages) {
        this.input = input;
        this.inboundMessages = inboundMessages;
    }

    public void run() {
        while (true) {
            try {
                String request = (String) input.readObject();
                if (!request.equals(PING)) {
                    inboundMessages.add(request);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

