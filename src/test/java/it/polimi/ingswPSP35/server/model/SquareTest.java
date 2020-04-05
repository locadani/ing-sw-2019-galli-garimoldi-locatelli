package it.polimi.ingswPSP35.server.model;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SquareTest {

    Square square1 = null;

    @Before
    public void setUp() {
        square1 = new Square(3, 2);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void isAdjacent_outputFalse() {
        Square square2 = new Square(4, 4);
        assertFalse(square1.isAdjacent(square2));
    }

    @Test
    public void isAdjacent_outputTrue() {
        Square square2 = new Square(2, 1);
        assertTrue(square1.isAdjacent(square2));
    }

    @Test
    public void isAdjacent_sameSquare_outputFalse() {
        assertFalse(square1.isAdjacent(square1));
    }
}