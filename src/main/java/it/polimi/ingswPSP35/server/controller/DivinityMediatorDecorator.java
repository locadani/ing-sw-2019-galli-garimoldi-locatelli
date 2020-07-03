package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;


/**
 * The presence of divinities that affect other players' turns requires a way for Divinities to be "consulted" outside
 * of their turn. This is achieved through the use of mediator pattern combined with decorator pattern (see
 * {@code DivinityMediator} for more details). <p>
 *
 * Because a 3 player game can become a 2 player game, it's important to be able to modify decorations at runtime, in case
 * the Divinity eliminated has decorated the mediator. This is through the {@code removeMediator} method. <p>
 *
 * Please note that all implementations of this class are inner classes of the respective divinity (Athena, Hera and Limus)
 *
 * @see it.polimi.ingswPSP35.server.controller.DivinityMediator
 */
public abstract class DivinityMediatorDecorator extends DivinityMediator {

    /**
     * Reference to the mediator this Decorator is decorating. This serves the same purpose of a NEXT pointer in a
     * singly-linked list, as it allows the mediator to change at runtime.
     */
    protected DivinityMediator decoratedMediator;

    /**
     * Sole constructor for this class. It's akin to an insertion at the front of a singly-linked list.
     * @param toDecorate {@code DivinityMediator} to be decorated by this class
     */
    public DivinityMediatorDecorator (DivinityMediator toDecorate) {
        this.decoratedMediator = toDecorate;
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

    /**
     * Returns the name of the {@code Divinity} this Decorator belongs to. It's used to identify Decorators during
     * their removal at runtime.
     * @return the name of the {@code Divinity} this Decorator belongs to
     */
    public abstract String getName();

    /**
     * Getter for {@code decoratedMediator} parameter. It's used for removal of decorators.
     * @return {@code decoratedMediator} parameter
     */
    public DivinityMediator getDecoratedMediator() {
        return decoratedMediator;
    }

    /**
     * Removes the {@code DivinityMediatorDecorator} identified by the String {@code toRemove}. If no such Decorator
     * exists, this method has no side effects.
     * @param toRemove String corresponding to the Decorator to be removed
     */
    public void removeDecorator(String toRemove) {
        //if the decorated mediator is not a decorator itself, it can only be the DivinityMediator, and therefore
        //cannot be removed. This only happens if removeDecorator is called when there is no decorator to remove
        if (!(decoratedMediator instanceof DivinityMediatorDecorator)) {
            return;
        //if the decorated mediator is the one to be removed, "skip it" by assigning the mediator decorated by the
        //current decorated mediator as the new decorated mediator of this instance (think of removal in a singly-linked list)
        } else if (((DivinityMediatorDecorator) decoratedMediator).getName().equals(toRemove)) {
            decoratedMediator = ((DivinityMediatorDecorator) decoratedMediator).getDecoratedMediator();
        } else {
        //if none of the above are true, make a recursive call
            ((DivinityMediatorDecorator) decoratedMediator).removeDecorator(toRemove);
        }
    }
}
