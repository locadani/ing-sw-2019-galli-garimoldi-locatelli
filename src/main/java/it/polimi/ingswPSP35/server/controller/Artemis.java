package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Square;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.List;


public class Artemis extends Divinity {

    @Override
    public void playTurn() {
        //TODO decide how to implement turn structure and client interaction
    }


    public class Turn {
        private List<Action> availableActions;
        private List<Action> actionsTaken;
        private Divinity divinity;

        //TODO define case-specific exceptions
        public void tryAction(Action action, Worker worker, Square square) throws Exception {
            if (availableActions.contains(action)) {
                switch (action) {
                    case BUILD:
                        divinity.build(square);
                        availableActions.clear();
                        availableActions.add(Action.ENDTURN);
                        break;
                    case MOVE:
                        divinity.selectWorker(worker);
                        divinity.move(square);
                        availableActions.remove(Action.MOVE);
                        availableActions.add(Action.GODPOWER);
                        break;
                    case GODPOWER:
                        divinity.move(square);
                        availableActions.remove(Action.GODPOWER);
                        break;
                    case ENDTURN:
                        reset();
                        break;
                }
            }
            else throw new Exception();
        }

        public void reset(){
            availableActions.clear();
            availableActions.add(Action.MOVE);
            divinity.selectWorker(null);
        }

        public void setDivinity(Divinity divinity) {
            this.divinity = divinity;
        }
    }
}
