package it.polimi.ingswPSP35.client.gui;

public enum PossibleAction {
    MOVE ("MOVE"),
    BUILD ("BUILD"),
    QUIT ("QUIT"),
    CLEAR ("CLEAR"),
    ENDTURN ("ENDTURN");

    public final String action;
    PossibleAction(String action)
    {
        this.action = action;
    }
}
