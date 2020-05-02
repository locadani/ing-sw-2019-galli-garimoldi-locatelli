package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.model.Dome;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class Atlas extends Divinity {
    private final String name = "Atlas";

    @Override
    public String getName() {
        return name;
    }

    public boolean buildDome(Square target) {
        Square workerSquare = board.getSquare(selectedWorker.getX(), selectedWorker.getY());
        if (canBuild(selectedWorker, workerSquare, target)) {
            target.insert(new Dome());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AbstractTurn getTurn() {
        return new Atlas.Turn();
    }

    public class Turn extends AbstractTurn {

        public Turn() {
            super();
        }

        private Turn(ArrayList<Action> availableActions, ArrayList<Action> actionsTaken) {
            super(availableActions, actionsTaken);
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
                            availableActions.add(Action.GODPOWER);
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
                        if (buildDome(square)) {
                            availableActions.clear();
                            actionsTaken.add(Action.GODPOWER);
                            availableActions.add(Action.ENDTURN);
                        }

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

        @Override
        public AbstractTurn copy() {
            return new Atlas.Turn(availableActions, actionsTaken);
        }
    }
}
