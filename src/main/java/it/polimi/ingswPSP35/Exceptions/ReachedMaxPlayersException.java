package it.polimi.ingswPSP35.Exceptions;

public class ReachedMaxPlayersException extends Exception{
    public ReachedMaxPlayersException() {}
    public ReachedMaxPlayersException(String message) {
        super(message);
    }
}
