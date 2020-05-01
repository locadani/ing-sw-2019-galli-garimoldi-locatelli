/**
 * Contains which worker performed what action and where
 */

package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.controller.divinities.Action;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public class RequestedAction {
    private Worker chosenWorker;
    private Square chosenSquare;
    private Action chosenAction;

    public RequestedAction()
    {}
    public Worker getWorker(){return chosenWorker;}
    public Square getSquare(){return chosenSquare;}
    public Action getAction(){return chosenAction;}
}
