package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;


/**
 * The presence of divinities that affect other players' turns requires a way for Divinities to be "consulted" ever outside
 * of their turn. This is achieved through the use of mediator pattern combined with decorator pattern (see
 * {@code DivinityMediator} for more details).
 */
public abstract class DivinityMediatorDecorator extends DivinityMediator {

    protected DivinityMediator decoratedMediator;

    public DivinityMediatorDecorator (DivinityMediator d) {
        this.decoratedMediator = d;
    }

    @Override
    public boolean checkMove(Worker workerToBeMoved, Square workerSquare, Square destination) {
        return decoratedMediator.checkMove(workerToBeMoved, workerSquare, destination);
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
        //if the decorated mediator is not a decorator itself, it can only be the DivinityMediator, and therefore
        //cannot be removed. This only happens if removeDecorator is called when there is no decorator to remove
        if (!(decoratedMediator instanceof DivinityMediatorDecorator)) {
            return;
        //if the decorated mediator is the one to be removed, "skip it" by assigning the mediator decorated by the
        //current decorated mediator as the new decorated mediator of this instance
        } else if (((DivinityMediatorDecorator) decoratedMediator).getName().equals(toRemove)) {
            decoratedMediator = ((DivinityMediatorDecorator) decoratedMediator).getDecoratedMediator();
        } else {
        //if none of the above are true, make a recursive call
            ((DivinityMediatorDecorator) decoratedMediator).removeDecorator(toRemove);
        }
    }
}
