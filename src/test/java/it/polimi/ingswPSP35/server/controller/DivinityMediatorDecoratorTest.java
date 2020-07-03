package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Worker;
import org.junit.Before;

public class DivinityMediatorDecoratorTest {
    DivinityMediator mediator = null;
    Divinity Limus = null;
    Divinity Hera = null;
    Board board = null;

    @Before
    public void setUp() {
        mediator = new DivinityMediator();
        Limus = DivinityFactory.create("Limus");
        Hera = DivinityFactory.create("Hera");

        mediator = Hera.decorate(Limus.decorate(mediator));
        board = new Board();

        Hera.setDivinityMediator(mediator);
        Hera.setBoard(board);
        Worker heraWorker = new Worker(new Coordinates(3), new Player("heraPlayer", 1));
        Hera.placeWorker(heraWorker, new Coordinates(3));
        Hera.selectWorker(new Coordinates(3));

        Limus.setDivinityMediator(mediator);

    }
}
