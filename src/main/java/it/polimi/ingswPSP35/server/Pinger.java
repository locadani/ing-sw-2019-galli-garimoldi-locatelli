/**
 * Class to ping every client via his OOS associated
 */
package it.polimi.ingswPSP35.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Pinger implements Runnable {
    /**
     * Each object is added in PlayerListRetriever
     * after socket connection
     */
    private List<ObjectOutputStream> clients;

    public Pinger() {
        clients = new ArrayList<>();
    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()) {
                Thread.sleep(2500);
                for (ObjectOutputStream client : clients) {
                    client.writeObject("PING");
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClient(ObjectOutputStream os)
    {
        clients.add(os);
    }
}
