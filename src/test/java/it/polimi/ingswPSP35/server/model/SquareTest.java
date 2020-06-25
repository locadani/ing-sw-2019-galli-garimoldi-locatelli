package it.polimi.ingswPSP35.server.model;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SquareTest {

    Square square1 = null;
    Square square2 = null;

    @Before
    public void setUp() {
        square1 = new Square(3, 2);
        square2 = new Square(2,2);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void isAdjacent_outputFalse() {
        Square square3 = new Square(4, 4);
        assertFalse(square1.isAdjacent(square3));
    }

    @Test
    public void isAdjacent_outputTrue() {
        Square square3 = new Square(2, 1);
        assertTrue(square1.isAdjacent(square3));
    }

    @Test
    public void isAdjacent_sameSquare_outputFalse() {
        assertFalse(square1.isAdjacent(square1));
    }

    @Test
    public void insertBlock_heightIncrease() {
        int prevHeight = square1.getHeight();
        square1.insert(new Block());
        assertEquals(prevHeight +1, square1.getHeight());
    }

    @Test
    public void insertDome_heightUnchanged() {
        int prevHeight = square1.getHeight();
        square1.insert(new Dome());
        assertEquals(prevHeight, square1.getHeight());
    }

    @Test
    public void isFree_worker_outputFalse() {
        square1.insert(new Worker(square1.getCoordinates(), new Player("A",1)));
        assertFalse(square1.isFree());
    }

    @Test
    public void isFree_outputTrue() {
        square1.insert(new Block());
        assertTrue(square1.isFree());
    }

    //rewrite with arrayList instead of Array[]
    @Test
    public void getPieceStack_outputTrue() {
        square1.insert(new Block());
        square1.insert(new Dome());
        square1.insert(new Dome());

        ArrayList<Piece> comparison = new ArrayList<>(3);
        comparison.add(0, new Block());
        comparison.add(1, new Dome());
        comparison.add(2, new Dome());

        for (int i = 0; i <= 2; i++) {
            assertSame(square1.getPieceStack().get(i).getClass(), comparison.get(i).getClass());
        }
    }

    //rewrite with arrayList instead of Array[]
    @Test
    public void getPieceStack_outputFalse() {
        square1.insert(new Block());
        square1.insert(new Dome());
        square1.insert(new Dome());
        square1.insert(new Worker(square1.getCoordinates(), new Player("A",1)));

        ArrayList<Piece> comparison = new ArrayList<>(4);
        comparison.add(0, new Block());
        comparison.add(1, new Block());
        comparison.add(2, new Block());
        comparison.add(3, new Worker(square1.getCoordinates(), new Player("A",1)));

        int matches = 0; int i;
        for (i = 0; i <= 3; i++) {
            if(square1.getPieceStack().get(i).getClass() == comparison.get(i).getClass()) {
                matches++;
            }
        }
        assertNotEquals(matches, i);
    }

    @Test
    public void removeBlockTest()
    {
        int height;
        square1.insert(new Block());
        height = square1.getHeight();
        square1.removeTop();
        assertTrue(height - 1 == square1.getHeight());
    }
}