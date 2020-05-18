package it.polimi.ingswPSP35.client;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServerPinger implements Runnable {

    private ObjectOutputStream outputStream;

    public ServerPinger(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void run() {


        try {
            while (!Thread.interrupted()) {
                Thread.sleep(2700);
                outputStream.writeObject("PING");
            }
        }
        catch (IOException e) {
        }
        catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

    public void add(ObjectOutputStream os) {
        outputStream = os;
    }
}