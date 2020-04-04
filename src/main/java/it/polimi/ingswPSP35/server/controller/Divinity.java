package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public abstract class Divinity {
    private String Name;
    private boolean isLegalFor3Players;


    public String getName() {
        return Name;
    }

    public boolean isLegalFor3Players() {
        return isLegalFor3Players;
    }


    /**
     *verifies if it is possible to move a Worker "w" to a Square "s"
     *
     * @author Paolo Galli
     * @param w Worker to be moved
     * @param s Square of destination
     * @return a boolean that expresses whether the move is allowed
     */
    public boolean Move(Worker w, Square s){
        if((s.isFree()) && s.isAdjacent(w.getSquare()){}
        return true;
    }
}