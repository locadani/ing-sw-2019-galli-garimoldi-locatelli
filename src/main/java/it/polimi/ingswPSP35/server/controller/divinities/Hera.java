package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.DivinityMediatorDecorator;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

public class Hera extends Divinity {
    private final static String name = "Hera";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbstractTurn getTurn() {
        return new DefaultTurn(this);
    }

    @Override
    public DivinityMediator decorate(DivinityMediator d) {
        return new Hera.Decorator(d);
    }

    private class Decorator extends DivinityMediatorDecorator {

        public Decorator(DivinityMediator d) {
            super(d);
        }

        @Override
        public boolean checkWin(Worker worker, Square workerSquare, Square origin) {

            //if Hera is trying to win, don't apply god power
            if (worker.getPlayer().getDivinity().getName().equals(name))
                return decoratedMediator.checkWin(worker, workerSquare, origin);

            if (workerSquare.isPerimetral()) {
                return false;
            }
            else return decoratedMediator.checkWin(worker, workerSquare, origin);
        }

        @Override
        public String getName() {
            return name;
        }
    }

}
