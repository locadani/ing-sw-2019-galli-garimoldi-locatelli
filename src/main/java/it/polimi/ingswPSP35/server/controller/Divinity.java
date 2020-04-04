package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public abstract class Divinity {
    private String Name;
    private boolean isLegalFor3Players;
    private boolean isLegalFor4Players;


    public String getName() {
        return Name;
    }

    public boolean isLegalFor3Players() { 
        return isLegalFor3Players;
    }

    public boolean isLegalFor4Players() {
        return isLegalFor4Players;
    }


    /**
     *verifies if it is possible to move a Worker "w" to a Square "s"
     *
     * @author Paolo Galli
     * @param w Worker to be moved
     * @param s Square of destination
     * @return a boolean that expresses whether the move is allowed
     */
    public boolean tryMove(Worker w, Square s){
        return true;
    }
}