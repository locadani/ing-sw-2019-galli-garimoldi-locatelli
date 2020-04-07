package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public abstract class DivinityMediatorDecorator extends DivinityMediator {
    private String name;
    private DivinityMediator decoratedMediator;

    public DivinityMediatorDecorator (DivinityMediator d) {
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

    public DivinityMediator getDecoratedMediator() {
        return decoratedMediator;
    }

    //TODO test decoration
    //TODO add a sentinel decorator as the outermost wrapper to allow deletion
    public void removeDecorator(DivinityMediatorDecorator toRemove) {
        if (!(decoratedMediator instanceof DivinityMediatorDecorator)) {
            return;
        } else if (decoratedMediator.equals(toRemove)) {
            decoratedMediator = ((DivinityMediatorDecorator) decoratedMediator).getDecoratedMediator();
        } else {
            ((DivinityMediatorDecorator) decoratedMediator).removeDecorator(toRemove);
        }
    }

}
