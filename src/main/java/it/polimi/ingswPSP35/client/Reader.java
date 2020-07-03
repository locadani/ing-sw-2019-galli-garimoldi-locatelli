package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.commons.MessageID;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

public class Reader implements Runnable {
    private final ObjectInputStream input;
    private static final String PING = "";
    private boolean running = true;
    LinkedBlockingQueue<String> inboundMessages;

    public Reader(ObjectInputStream input, LinkedBlockingQueue<String> inboundMessages) {
        this.input = input;
        this.inboundMessages = inboundMessages;
    }

    public void run() {
        while (running) {
            try {
                String request = (String) input.readObject();
                if (!request.equals(PING)) {
                    inboundMessages.add(request);
                }
            } catch (EOFException  e) {
                break;
            } catch (SocketException e) {
                inboundMessages.add(MessageID.NOTIFICATION + ":Server Crashed");
                running = false;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Disconnected");
                break;
            }
        }
    }
}

