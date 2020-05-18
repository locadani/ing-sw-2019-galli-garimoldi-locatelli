package it.polimi.ingswPSP35.server.Exceptions;

import it.polimi.ingswPSP35.server.model.Player;

import java.io.IOException;

public class DisconnectedException extends Exception{
    private String name;
    public DisconnectedException(String name)
    {
        this.name = name;
    }
    public String getIs()
    {
        return name;
    }
}
