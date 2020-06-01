package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.RequestedAction;

public class Request {

    //al posto che int coordinates e enum
    private int worker;
    private Action action;
    private int destination;
    private boolean workerSelected;
    private boolean ready;

    public Request()
    {
        ready = false;
        workerSelected = false;
        worker = 0;
        action = null;
        destination = 0;
    }

    public int getWorker() {
        return worker;
    }

    public void setCell(int cell) {
        if(action == Action.ENDTURN)
            ready = true;
        else if(action != null) {
            if (!workerSelected) {
                this.worker = cell;
                workerSelected = true;
            } else {
                ready = true;
                this.destination = cell;
            }
        }
        else
            this.worker = cell;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
        workerSelected = false;
    }

    public int getDestination() {
        return destination;
    }

    public void print()
    {
        System.out.println("Contains: " + worker +" " + action + " " + destination);
    }

    public String getInfo()
    {
        return worker + ":" + action.toString() + ":" + destination;
    }

    public void reset()
    {
        worker = 0;
        destination = 0;
        workerSelected = false;
        ready = false;
    }

    public RequestedAction getRequestedAction() {
        return new RequestedAction(worker, action, destination);
    }

    public boolean isReady()
    {
        return ready;
    }
}
