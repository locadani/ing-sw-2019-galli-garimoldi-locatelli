package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public abstract class DivinityMediatorDecorator extends DivinityMediator {

    protected DivinityMediator decoratedMediator;

    //TODO controllare costruttore
    public DivinityMediatorDecorator (DivinityMediator d) {
        this.decoratedMediator = d;
    }

    @Override
    public boolean checkMove(Worker worker, Square workerSquare, Square destination) {
        return super.checkMove(worker, workerSquare, destination);
    }

    @Override
    public boolean checkBuild(Worker worker, Square workerSquare, Square destination) {
        return super.checkBuild(worker, workerSquare, destination);
    }

    @Override
    public boolean checkWin(Worker worker, Square workerSquare, Square destination) {
        return super.checkWin(worker, workerSquare, destination);
    }

    public abstract String getName();

    public DivinityMediator getDecoratedMediator() {
        return decoratedMediator;
    }

    //TODO test decoration
    //TODO add a sentinel decorator as the outermost wrapper to allow deletion
    public void removeDecorator(String toRemove) {
        if (!(decoratedMediator instanceof DivinityMediatorDecorator)) {
            return;
        } else if (((DivinityMediatorDecorator) decoratedMediator).getName().equals(toRemove)) {
            decoratedMediator = ((DivinityMediatorDecorator) decoratedMediator).getDecoratedMediator();
        } else {
            ((DivinityMediatorDecorator) decoratedMediator).removeDecorator(toRemove);
        }
    }

}
