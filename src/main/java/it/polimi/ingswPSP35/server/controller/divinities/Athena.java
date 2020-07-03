package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.controller.*;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

/**
 * This class is the implementation of the divinity Athena. Athena prevents other divinities by moving up if she has
 * moved up on her turn. This behaviour is achieved by decorating the mediator and updating it every turn.
 */
public class Athena extends Divinity {
    private final String name = "Athena";
    private Decorator athenaDecorator;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean move(Coordinates destination) {
        int initialHeight = board.getSquare(selectedWorker.getCoordinates()).getHeight();
        if (super.move(destination)) {
            updateMediator(board.getSquare(destination).getHeight() > initialHeight);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DivinityMediator decorate(DivinityMediator d) {

        athenaDecorator = new Athena.Decorator(d);
        return athenaDecorator;
    }

    private void updateMediator (boolean HasMovedUp) {
        athenaDecorator.setHasMovedUp(HasMovedUp);
    }

    private class Decorator extends DivinityMediatorDecorator {

        private boolean athenaHasMovedUp;

        public Decorator(DivinityMediator d) {
            super(d);
        }

        @Override
        public boolean checkMove(Worker worker, Square workerSquare, Square destination) {
            //if worker is Athena's, check other decorations
            if(worker.getPlayer().getDivinity().getName().equals("Athena")){
                return decoratedMediator.checkMove(worker, workerSquare, destination);
            }
            //check Athena's godpower
            else if(workerSquare.getHeight() < destination.getHeight()
                && athenaHasMovedUp) {
                    return false;
                }
            else return decoratedMediator.checkMove(worker, workerSquare, destination);
        }

        @Override
        public String getName() {
            return name;
        }

        public void setHasMovedUp(boolean hasMovedUp) {
            this.athenaHasMovedUp = hasMovedUp;
        }
    }
}
