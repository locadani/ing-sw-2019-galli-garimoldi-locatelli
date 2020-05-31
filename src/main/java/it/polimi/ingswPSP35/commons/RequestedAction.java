

package it.polimi.ingswPSP35.commons;

public class RequestedAction {
    private final Coordinates workerCoordinates;
    private final Coordinates squareCoordinates;
    private Action chosenAction;

    public RequestedAction(int worker, Action action, int square) {
        workerCoordinates = new Coordinates(worker);
        chosenAction = action;
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
