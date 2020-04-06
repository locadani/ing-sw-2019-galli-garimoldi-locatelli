package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public class Artemis extends Divinity {

    @Override
    public void playTurn() {
        //TODO decide how to implement turn structure and client interaction
    }

    public void useGodPower(Square square) {
        super.move(square);
    }
}
