package it.polimi.ingswPSP35.server.controller.divinities;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.controller.DivinityMediator;
import it.polimi.ingswPSP35.server.controller.Winner;
import it.polimi.ingswPSP35.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DivinityTest {

    private Board board;
    private Player player;
    private Worker worker;
    private Divinity divinity;
    private Square origin, destination;
    private Winner winner;
    private DivinityMediator divinityMediator;


    @Before
    public void setUp()
    {
        /*
        Player worker is on height two tower in cell 1
        Height three tower in cell 2
         */
        winner = new Winner();
        divinity = DivinityFactory.create("Apollo");
        divinityMediator = new DivinityMediator();
        divinity.setWinner(winner);
        player = new Player("Me", 1);
        player.setDivinity(divinity);
        worker = new Worker(new Coordinates(1), player);
        divinityMediator = player.getDivinity().decorate(divinityMediator);
        divinity.setDivinityMediator(divinityMediator);
        player.addWorker(worker);
        board = new Board();
        origin = board.getSquare(new Coordinates(1));
        origin.insert(new Block());
        origin.insert(new Block());
        origin.insert(worker);
        destination = board.getSquare(new Coordinates(2));
        destination.insert(new Block());
        destination.insert(new Block());
        destination.insert(new Block());
    }

    @Test
    public void checkWin() {
        divinity.checkWin(worker, destination, origin);
        assertTrue(winner != null);
    }
}