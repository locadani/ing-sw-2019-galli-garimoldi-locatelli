package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.server.controller.*;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;

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

    @Override
    public AbstractTurn getTurn() {
        return new Athena.Turn();
    }


    private class Turn extends AbstractTurn {

        public Turn() {
            super();
        }

        private Turn(ArrayList<Action> availableActions, ArrayList<Action> actionsTaken) {
            super(availableActions, actionsTaken);
        }

        public boolean tryAction(Coordinates workerCoordinates, Action action, Coordinates squareCoordinates) {

            if(actionsTaken.isEmpty())
                selectWorker(workerCoordinates);

            if (availableActions.contains(action)) {
                switch (action) {
                    case MOVE:
                        if (move(squareCoordinates)) {
                            actionsTaken.add(Action.MOVE);
                            availableActions.clear();
                            availableActions.add(Action.BUILD);
                            return true;
                        }
                        break;

                    case BUILD:
                        if (build(squareCoordinates)) {
                            availableActions.clear();
                            actionsTaken.add(Action.BUILD);
                            availableActions.add(Action.ENDTURN);
                            return true;
                        }
                        break;

                    case GODPOWER:
                        return false;

                    case ENDTURN:
                        reset();
                        return true;
                }
            }
            return false;
        }


        @Override
        public AbstractTurn copy() {
            return new Athena.Turn(this.availableActions, this.actionsTaken);
        }
    }
}
