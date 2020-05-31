package it.polimi.ingswPSP35.client.gui;

public class Request {
    //al posto che int coordinates e enum
    private int worker;
    private PossibleAction possibleAction;
    private int destination;
    private boolean workerSelected;

    public Request()
    {
        workerSelected = false;
        worker = 0;
        possibleAction = PossibleAction.CLEAR;
        destination = 0;
    }

    public int getWorker() {
        return worker;
    }

    public void setCell(int cell) {
        if(possibleAction != PossibleAction.CLEAR) {
            if (!workerSelected) {
                this.worker = cell;
                workerSelected = true;
            } else
                this.destination = cell;
        }
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

    private void reset()
    {
        worker = 0;
        destination = 0;
    }
}
