package it.polimi.ingswPSP35.client.gui;

public class Request {

    //al posto che int coordinates e enum
    private int worker;
    private PossibleAction possibleAction;
    private int destination;
    private boolean workerSelected;
    private boolean ready;

    public Request()
    {
        ready = false;
        workerSelected = false;
        worker = 0;
        possibleAction = PossibleAction.CLEAR;
        destination = 0;
    }

    public int getWorker() {
        return worker;
    }

    public void setCell(int cell) {
        if(possibleAction == PossibleAction.ENDTURN)
            ready = true;
        else if(possibleAction != PossibleAction.CLEAR) {
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

    public PossibleAction getPossibleAction() {
        reset();
        return possibleAction;
    }

    public void setPossibleAction(PossibleAction possibleAction) {
        this.possibleAction = possibleAction;
        workerSelected = false;
    }

    public int getDestination() {
        return destination;
    }

    public void print()
    {
        System.out.println("Contains: " + worker +" " + possibleAction + " " + destination);
    }

    public String getInfo()
    {
        return worker + ":" + possibleAction.toString() + ":" + destination;
    }

    public void reset()
    {
        worker = 0;
        destination = 0;
        workerSelected = false;
        ready = false;
    }

    public boolean isReady()
    {
        return ready;
    }
}
