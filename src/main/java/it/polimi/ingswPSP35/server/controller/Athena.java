package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;

public class Athena extends Divinity {
    private final String Name = "Athena";
    private Decorator athenaDecorator;
    private final boolean decorates = true;

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

    @Override
    public AbstractTurn getTurn() {
        return new Athena.Turn();
    }


    private class Turn extends AbstractTurn {
        private List<Action> availableActions;
        private List<Action> actionsTaken;

        public Turn() {
            reset();
        }

        private Turn(List<Action> availableActions, List<Action> actionsTaken) {
            this.availableActions = List.copyOf(availableActions);
            this.actionsTaken = List.copyOf(actionsTaken);
        }

        public boolean tryAction(Action action, Worker worker, Square square) {
            if (availableActions.contains(action)) {
                switch (action) {
                    case MOVE:
                        if (move(square)) {
                            selectWorker(worker);
                            actionsTaken.add(Action.MOVE);
                            availableActions.clear();
                            availableActions.add(Action.BUILD);
                            return true;
                        }
                    case BUILD:
                        if (build(square)) {
                            availableActions.clear();
                            actionsTaken.add(Action.BUILD);
                            availableActions.add(Action.ENDTURN);
                            return true;
                        }
                    case GODPOWER:
                        return false;
                    case ENDTURN:
                        reset();
                        return true;
                }
            }
            return false;
        }

        public void reset() {
            availableActions.clear();
            actionsTaken.clear();
            availableActions.add(Action.MOVE);
            selectWorker(null);
        }

        public List<Action> getActionsTaken() {
            return actionsTaken;
        }

        public List<Action> getAvailableActions() {
            return availableActions;
        }

        @Override
        public AbstractTurn copy() {
            return new Athena.Turn(this.availableActions, this.actionsTaken);
        }
    }
}
