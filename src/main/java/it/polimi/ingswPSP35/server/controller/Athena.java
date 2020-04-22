package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public class Athena extends Divinity {
    private final String Name = "Athena";
    private Decorator athenaDecorator;
    private final boolean decorates = true;


    @Override
    public void playTurn() {
        //code
    }

    @Override
    public boolean move(Square destination) {
        int initialHeight = board.getSquare(selectedWorker.getX(),selectedWorker.getY()).getHeight();
        if (super.move(destination)) {
            updateMediator(destination.getHeight() > initialHeight);
            return true;
        } else {
            return false;
        }
    }

    public DivinityMediator DecorateMediator (DivinityMediator d) {
        //TODO decide how to handle decoration
        return new Decorator(d);
    }

    private void updateMediator (boolean HasMovedUp) {
        athenaDecorator.setHasMovedUp(HasMovedUp);
    }

    //TODO should custom decorators be inner classes?
    private class Decorator extends DivinityMediatorDecorator {

        private boolean athenaHasMovedUp;

        public Decorator(DivinityMediator d) {
            super(d);
        }

        @Override
        public boolean checkMove(Worker worker, Square workerSquare, Square destination) {
            if(worker.getPlayer().getDivinity().getName().equals("Athena")){
                return super.checkMove(worker, workerSquare, destination);
            }
            else if(workerSquare.getHeight() < destination.getHeight()
                && athenaHasMovedUp) {
                    return false;
                }
            else return super.checkMove(worker, workerSquare, destination);
        }

        public void setHasMovedUp(boolean hasMovedUp) {
            this.athenaHasMovedUp = hasMovedUp;
        }
    }

}
