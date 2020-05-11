package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.VView.InternalClient;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.Iterator;
import java.util.List;

public class CheckConnection implements Runnable {
    private List<InternalClient> clients;
    private Player disconnectedPlayer;
    private Iterator<InternalClient> iterator = clients.iterator();
    public CheckConnection(List<InternalClient> clients, Player disconnectedPlayer) {
        this.disconnectedPlayer = disconnectedPlayer;
        this.clients = clients;
    }

    @Override
    public void run() {

        InternalClient current;
        String receivedMessage;
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (connectionList) {
                current = iterator.next();
                current.send("PING");
                Thread.sleep(1500);
                receivedMessage = current.receive();
                if(!receivedMessage.equals("PONG"))
                    disconnectedPlayer = current
            }
        }

    }

    public void removePlayer(Player player) {
        synchronized (connectionList) {
            connectionList.remove(player.getUsername());
        }
    }
}
