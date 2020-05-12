package it.polimi.ingswPSP35.Exceptions;

import it.polimi.ingswPSP35.server.model.Player;

public class PlayerQuitException extends Exception{
    private final Player player;

    public PlayerQuitException(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return player;
    }
}
