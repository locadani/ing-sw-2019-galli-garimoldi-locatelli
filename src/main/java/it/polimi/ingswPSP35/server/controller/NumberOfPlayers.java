package it.polimi.ingswPSP35.server.controller;

public class NumberOfPlayers {
    int nPlayers;
    public NumberOfPlayers(int value)
    {
        nPlayers= value;
    }
    public int getNumberOfPlayers()
    {
        return nPlayers;
    }

    public void setNumberOfPlayers(int nPlayers) {
        this.nPlayers = nPlayers;
    }
}
