/**
 * Contains which worker performed what action and where
 */

package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.controller.divinities.Action;
import it.polimi.ingswPSP35.server.model.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public class RequestedAction {
    private Coordinates workerCoordinates;
    private Coordinates squareCoordinates;
    private Action chosenAction;

    public RequestedAction(int worker, String action, int square) {
        workerCoordinates = new Coordinates(worker);
        switch (action) {
            case "MOVE":
                chosenAction = Action.MOVE;
                break;

            case "BUILD":
                chosenAction = Action.BUILD;
                break;

            case "ENDTURN":
                chosenAction = Action.ENDTURN;
                break;

            case "GODPOWER":
                chosenAction = Action.GODPOWER;
                break;

            case "QUIT":
                chosenAction = Action.QUIT;
                break;

        }
        squareCoordinates = new Coordinates(square);
    }

    public Coordinates getWorker() {
        return workerCoordinates;
    }

    public Coordinates getSquare() {
        return squareCoordinates;
    }

    public Action getAction() {
        return chosenAction;
    }
}
