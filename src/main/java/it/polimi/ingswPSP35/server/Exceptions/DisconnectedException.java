package it.polimi.ingswPSP35.server.Exceptions;

import it.polimi.ingswPSP35.server.model.Player;

import java.io.IOException;

public class DisconnectedException extends IOException{
    private String name;
    public DisconnectedException (){}
    public DisconnectedException(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
}
