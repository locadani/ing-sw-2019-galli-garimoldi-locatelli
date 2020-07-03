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
        reset();
    }

    public int getWorker() {
        return worker;
    }

    public void setCell(int cell) {
        if(action != null) {
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


    public void setAction(Action action) {
        this.action = action;
        workerSelected = false;
        if (action == Action.ENDTURN)
            ready = true;
    }

    public void reset()
    {
        worker = 0;
        destination = 0;
        workerSelected = false;
        ready = false;
        action = null;
    }

    public RequestedAction getRequestedAction() {
        RequestedAction toSend = new RequestedAction(worker, action, destination);
        reset();
        return toSend;
    }

    public boolean isReady()
    {
        return ready;
    }
}
