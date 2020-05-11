package it.polimi.ingswPSP35.Exceptions;

import it.polimi.ingswPSP35.server.model.Player;

import java.io.IOException;

public class ClientDisconnectedException extends IOException {
    Player player;

    public ClientDisconnectedException()
    {}
    public ClientDisconnectedException(Player player)
    {
        this.player = player;
    }
    public Player getDisconnectedPlayer()
    {
        return player;
    }
}
