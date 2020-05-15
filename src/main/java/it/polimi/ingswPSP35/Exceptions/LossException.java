package it.polimi.ingswPSP35.Exceptions;

import it.polimi.ingswPSP35.server.model.Player;

public class LossException extends Exception{
    private final Player loser;
    public LossException (Player player){
        this.loser = player;
    }
    public Player getLoser()
    {return loser;}
}
