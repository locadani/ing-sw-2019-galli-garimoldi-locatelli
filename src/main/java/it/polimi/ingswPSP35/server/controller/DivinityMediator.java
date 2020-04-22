package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public class DivinityMediator {

    //TODO refactor: pass Square instead of worker as first parameter: it's better practice and allows a smoother implementation of DefeatCheckerClass
    //TODO add currentDivinity reference to the divinity asking for checks, in order to compensate the loss of information caused but removing Worker parameter

    public boolean checkMove(Worker worker, Square workerSquare, Square destination) {
        return true;
    }

    public boolean checkBuild(Worker worker,Square workerSquare, Square destination) {
        return true;
    }

    //note: almost all win conditions are checked after moving
    public boolean checkWin(Worker worker, Square workerSquare, Square origin) {
        return true;
    }

}
