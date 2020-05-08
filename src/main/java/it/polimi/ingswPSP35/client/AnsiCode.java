package it.polimi.ingswPSP35.client;

public enum AnsiCode {

    BLUE("\u001b[36m"),
    GREEN("\u001B[32m"),
    PURPLE("\u001B[35m"),
    RED("\u001B[31m");


    static final String RESET = "\u001B[0m";


    private String escape;


    AnsiCode(String escape) {
        this.escape = escape;
    }


    public String getEscape() {
        return escape;
    }


    @Override
    public String toString() {
        return escape;
    }


}
