package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

/**
 * This class is the implementation of the divinity Pan. Pan is the only divinity (implemented) that has an additional
 * win condition, which is moving down from two or more levels. <p>
 * This behaviour is achieved by overriding the {@code checkWin} method.

 * @author Paolo Galli
 */
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
