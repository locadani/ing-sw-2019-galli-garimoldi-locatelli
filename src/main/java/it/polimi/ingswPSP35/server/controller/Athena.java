package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public class Athena extends Divinity {
    private String Name = "Athena";
    private Decorator athenaDecorator;


    @Override
    public void playTurn() {
        //code
    }

    @Override
    public boolean move(Square destination) {
        int initialHeight = selectedWorker.getSquare().getHeight();
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
        public boolean checkMove(Worker worker, Square destination) {
            if(worker.getPlayer().getDivinity().getName().equals("Athena")){
                return super.checkMove(worker, destination);
            }
            else if(worker.getSquare().getHeight() < destination.getHeight()
                && athenaHasMovedUp) {
                    return false;
                }
            else return super.checkMove(worker, destination);
        }

        public void setHasMovedUp(boolean hasMovedUp) {
            this.athenaHasMovedUp = hasMovedUp;
        }
    }

}
