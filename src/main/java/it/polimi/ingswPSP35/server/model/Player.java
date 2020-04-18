package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.server.controller.Divinity;

import java.util.ArrayList;

public class Player {
    private String username;
    private String age;
    private Divinity divinity;
    private ArrayList<Worker> workerList;
    private int colour;


    public void setDivinity(Divinity d) {
        divinity = d;
    }

    public Divinity getDivinity() {
        return divinity;
    }

    public ArrayList<Worker> getWorkerList() {
        return workerList;
    }
}
