package it.polimi.ingswPSP35.commons;

import java.util.concurrent.LinkedBlockingQueue;

public class Pinger implements Runnable {

    private static final String PING = "";
    private final LinkedBlockingQueue<Object> outgoingMessages;

    public Pinger(LinkedBlockingQueue<Object> output) {
        this.outgoingMessages = output;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
                outgoingMessages.add(PING);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}