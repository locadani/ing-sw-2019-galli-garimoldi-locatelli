package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public abstract class AbstractDivinityMediatorDecorator extends DivinityMediator {
    private String name;
    private DivinityMediator decoratedMediator;

    public AbstractDivinityMediatorDecorator (DivinityMediator d) {
        this.decoratedMediator = d;
    }

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

    public String getName() {
        return name;
    }

    //TODO add a collection of decorators that need to be re-added to the modified mediator
    public void removeDecorator(String toRemove){
        if (this.getName().equals(toRemove)) {
            //incompleto
        }
    }

}
