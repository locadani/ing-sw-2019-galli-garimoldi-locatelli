package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public abstract class AbstractDivinityMediatorDecorator extends DivinityMediator {
    private String name;

    @Override
    public boolean checkMove(Worker worker, Square destination) {
        return super.checkMove(worker, destination);
    }

    @Override
    public boolean checkBuild(Worker worker, Square destination) {
        return super.checkBuild(worker, destination);
    }

    @Override
    public boolean checkWin(Worker worker, Square destination) {
        return super.checkWin(worker, destination);
    }

    public void removeDecorator(String name){

    }
}
