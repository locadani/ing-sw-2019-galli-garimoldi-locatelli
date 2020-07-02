package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.DivinityMediatorDecorator;
import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class Limus extends Divinity {
    private final static String name = "Limus";
    private List<Worker> workerList = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean placeWorker(Worker worker, Coordinates coordinates) {
        if (super.placeWorker(worker, coordinates)) {
            workerList.add(worker);
            return true;
        } else return false;
    }



    @Override
    public DivinityMediator decorate(DivinityMediator d) {
        return new Limus.Decorator(d, workerList);
    }

    private class Decorator extends DivinityMediatorDecorator {
        private final List<Worker> workerList;

        public Decorator(DivinityMediator d, List<Worker> workerList) {
            super(d);
            this.workerList = workerList;
        }

        @Override
        public boolean checkBuild(Worker worker, Square workerSquare, Square destination) {
            //if Limus is trying to build, don't apply god power
            if (worker.getPlayer().getDivinity().getName().equals(name))
                return decoratedMediator.checkBuild(worker, workerSquare, destination);
            for (Worker w : workerList) {
                if (destination.isAdjacent(w.getCoordinates()) && destination.getHeight() != 3)
                    return false;
            }
            return decoratedMediator.checkBuild(worker, workerSquare, destination);
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
