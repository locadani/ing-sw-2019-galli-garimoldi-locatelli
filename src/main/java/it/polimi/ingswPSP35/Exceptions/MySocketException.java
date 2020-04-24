package it.polimi.ingswPSP35.Exceptions;

import java.io.IOException;

public class MySocketException extends Exception {
    public MySocketException(String playerName)
    {

        System.out.println("Costruttore");
    }
}
