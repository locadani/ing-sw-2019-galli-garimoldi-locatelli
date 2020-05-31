package it.polimi.ingswPSP35.server.Exceptions;

public class AbortMatchException extends Exception {
    String message;
    public AbortMatchException(String message)
    {
        this.message = message;
    }
    public String getMessage()
    {
        return message;
    }
}
