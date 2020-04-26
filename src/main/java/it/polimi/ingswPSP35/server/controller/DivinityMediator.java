package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public class DivinityMediator {

    public boolean checkMove(Worker worker, Square workerSquare, Square destination) {
        return true;
    }

    public boolean checkBuild(Worker worker,Square workerSquare, Square target) {
        return true;
    }

    //note: most win conditions are checked after moving
    public boolean checkWin(Worker worker, Square workerSquare, Square origin) {
        return true;
    }
}
