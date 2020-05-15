package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.model.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class Pan extends Divinity {

    private final String name = "Pan";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void checkWin(Worker worker, Square current, Square origin) {
        if(((origin.getHeight() == 2)
                && (current.getHeight() == 3)
                || (origin.getHeight() - current.getHeight() >= 2))
                && divinityMediator.checkWin(worker, current, origin))
            winner.setWinner(this);

    }

    @Override
    public AbstractTurn getTurn() {
        return new Pan.Turn();
    }

    public class Turn extends AbstractTurn {

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
            return new Pan.Turn(availableActions, actionsTaken);
        }
    }
}
