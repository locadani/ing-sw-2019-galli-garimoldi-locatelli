package it.polimi.ingswPSP35.server;

import java.io.IOException;

public class MySocketException extends Exception {
    public MySocketException(String playerName)
    {

        System.out.println("Costruttore");
    }
}
