package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public abstract class DivinityMediatorDecorator extends DivinityMediator {

    protected DivinityMediator decoratedMediator;

    public DivinityMediatorDecorator (DivinityMediator d) {
        this.decoratedMediator = d;
    }

    @Override
    public boolean checkMove(Worker worker, Square workerSquare, Square destination) {
        return decoratedMediator.checkMove(worker, workerSquare, destination);
    }

    @Override
    public boolean checkBuild(Worker worker, Square workerSquare, Square destination) {
        return decoratedMediator.checkBuild(worker, workerSquare, destination);
    }

    @Override
    public boolean checkWin(Worker worker, Square workerSquare, Square destination) {
        return decoratedMediator.checkWin(worker, workerSquare, destination);
    }

    public abstract String getName();

    public DivinityMediator getDecoratedMediator() {
        return decoratedMediator;
    }

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
