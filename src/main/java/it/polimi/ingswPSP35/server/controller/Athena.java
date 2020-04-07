package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public class Athena extends Divinity {
    private String Name = "Athena";
    private AthenaDecorator athenaDecorator;


    @Override
    public void playTurn() {
        //code
    }

    @Override
    public boolean move(Square destination) {
        int initialHeight = selectedWorker.getSquare().getHeight();
        if (super.move(destination)) {
            if ((destination.getHeight() > initialHeight)) {
                updateMediator(true);
            } else {
                updateMediator(false);
            }
            return true;
        } else {
            return false;
        }
    }

    public DivinityMediator DecorateMediator (DivinityMediator d) {
        //TODO decide how to handle decoration
        return new AthenaDecorator(d);
    }

    private void updateMediator (boolean HasMovedUp) {
        athenaDecorator.setHasMoveUp(HasMovedUp);
    }


    private class AthenaDecorator extends DivinityMediatorDecorator {

        private boolean hasMovedUp;

        public AthenaDecorator(DivinityMediator d) {
            super(d);
        }

        @Override
        public boolean checkMove(Worker worker, Square destination) {
            if(worker.getPlayer().getDivinity().getName().equals("Athena")){
                return super.checkMove(worker, destination);
            }
            else if(worker.getSquare().getHeight() < destination.getHeight()
                && hasMovedUp) {
                    return false;
                }
            else return super.checkMove(worker, destination);
        }

        public void setHasMoveUp(boolean hasMoveUp) {
            this.hasMovedUp = hasMoveUp;
        }
    }

}
