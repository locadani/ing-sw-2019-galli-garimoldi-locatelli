package it.polimi.ingswPSP35.server.model;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class ConcreteSquareTest {

    ConcreteSquare square1 = null;
    ConcreteSquare square2 = null;

    @Before
    public void setUp() {
        square1 = new ConcreteSquare(3, 2);
        square2 = new ConcreteSquare(2,2);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void isAdjacent_outputFalse() {
        ConcreteSquare square3 = new ConcreteSquare(4, 4);
        assertFalse(square1.isAdjacent(square3));
    }

    @Test
    public void isAdjacent_outputTrue() {
        ConcreteSquare square3 = new ConcreteSquare(2, 1);
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
        square1.insert(new Worker(square1, new Player()));
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
        square1.insert(new Worker(square1, new Player()));

        ArrayList<Piece> comparison = new ArrayList<>(4);
        comparison.add(0, new Block());
        comparison.add(1, new Block());
        comparison.add(2, new Block());
        comparison.add(3, new Worker(square1, new Player()));

        int matches = 0; int i;
        for (i = 0; i <= 3; i++) {
            if(square1.getPieceStack().get(i).getClass() == comparison.get(i).getClass()) {
                matches++;
            }
        }
        assertNotEquals(matches, i);
    }
}