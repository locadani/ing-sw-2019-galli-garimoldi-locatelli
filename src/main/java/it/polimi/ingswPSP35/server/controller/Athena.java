package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public class Athena extends Divinity {
    private boolean hasMovedUp;


    @Override
    public void playTurn() {
        hasMovedUp = false;
        //code
    }

    @Override
    public boolean move(Square destination) {
        int initialHeight = selectedWorker.getSquare().getHeight();
        if (super.move(destination)) {
            if (destination.getHeight() > initialHeight) {
                hasMovedUp = true;
            }
            return true;
        } else {
            return false;
        }
    }

    private class AthenaDecorator extends AbstractDivinityMediatorDecorator {

        public AthenaDecorator(DivinityMediator d) {
            super(d);
        }

        @Override
        public boolean checkMove(Worker worker, Square destination) {
            if(worker.getSquare().getHeight() < destination.getHeight()) {
                return false;
            }
            else return super.checkMove(worker, destination);
        }
    }

    public DivinityMediator DecorateMediator (DivinityMediator d) {
        //TODO decide how to handle decoration
        return new AthenaDecorator(d);
    }
}
