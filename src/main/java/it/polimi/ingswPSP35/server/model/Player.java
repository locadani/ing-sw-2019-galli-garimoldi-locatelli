package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.server.controller.Divinity;

public class Player {
    private String username;
    private String age;
    private Divinity divinity;
    private Worker workerM;
    private Worker workerF;
    private int colour;

    public void initializePlayer() {
    }

    public void setDivinity(Divinity d) {
        divinity = d;
    }

    public Divinity getDivinity() {
        return divinity;
    }

}
