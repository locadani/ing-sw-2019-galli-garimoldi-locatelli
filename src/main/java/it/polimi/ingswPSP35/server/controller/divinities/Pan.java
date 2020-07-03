package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;

public class Pan extends Divinity {

    private final String name = "Pan";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void checkWin(Worker worker, Square current, Square origin) {
        if(((origin.getHeight() == 2)
                && (current.getHeight() == 3)
                || (origin.getHeight() - current.getHeight() >= 2))
                && divinityMediator.checkWin(worker, current, origin))
            winner.setWinner(this);

    }

}
