package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.controller.divinities.Divinity;

public class Winner {
    private Divinity winner;

    public Winner()
    {
        winner = null;
    }

    public Divinity getWinner() {
        return winner;
    }

    public void setWinner(Divinity winner) {
        this.winner = winner;
    }

}
