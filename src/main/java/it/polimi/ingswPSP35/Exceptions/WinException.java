package it.polimi.ingswPSP35.Exceptions;

import it.polimi.ingswPSP35.server.model.Player;

public class WinException extends Exception{
    Player winner;
    public WinException(Player winner)
    {
        this.winner = winner;
    }
    public Player getWinner()
    {
        return winner;
    }
}
