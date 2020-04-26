package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;

public class Pan extends Divinity {

    private final String name = "Pan";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean checkWin(Worker worker, Square current, Square origin) {
        return  ((origin.getHeight() == 2)
                && (current.getHeight() == 3)
                || origin.getHeight() - current.getHeight() >= 2)
                && divinityMediator.checkWin(worker, current, origin);
    }

    @Override
    public AbstractTurn getTurn() {
        return new Pan.Turn();
    }

    public class Turn extends AbstractTurn {

        private List<Action> availableActions;
        private List<Action> actionsTaken;

        public Turn() {
            reset();
        }

        private Turn(List<Action> availableActions, List<Action> actionsTaken){
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
            return List.copyOf(actionsTaken);
        }

        public List<Action> getAvailableActions() {
            return List.copyOf(availableActions);
        }

        @Override
        public AbstractTurn copy() {
            return new Pan.Turn(availableActions, actionsTaken);
        }
    }
}
