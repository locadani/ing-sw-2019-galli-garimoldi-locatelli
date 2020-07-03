package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.ReducedSquare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static java.lang.Math.abs;

/**
 * This class represents one of the 25 spaces that the {@code Board} is made of. <p>
 * Each {@code Square} can contain up to 4 {@code Piece}s. <p>
 * This class also contains methods to check common {@code Divinity} conditions, such as adjacency between {@code Square}s,
 * and a method that returns a copy to allow {@code DefeatChecker} to simulate actions causing side effects to the
 * {@code Board} instance shared by all Divinities
 *
 * @author Paolo Galli
 */
public class Square {
    private final Coordinates coordinates;
    private int height = 0;
    private final Stack<Piece> pieceStack;

    /**
     * Constructor that accepts {@code Coordinates} as input to identify the position of this {@code Square}.
     *
     * @param coordinates {@code Coordinates} corresponding to this {@code Square}'s position
     */
    Square(Coordinates coordinates) {
        this.coordinates = coordinates;
        pieceStack = new Stack<Piece>();
    }

    /**
     * Constructor that accepts two integers as input to identify the position of this {@code Square}.
     *
     * @param r number of row
     * @param c number of column
     */
    Square(int r, int c) {
        this.coordinates = new Coordinates(r, c);
        pieceStack = new Stack<Piece>();
    }

    /**
     * Getter for the row number of this {@code Square}.
     * @return row number of this {@code Square}
     */
    public int getC() {
        return coordinates.getC();
    }

    /**
     * Getter for the column number of this {@code Square}.
     * @return column number of this {@code Square}
     */
    public int getR() {
        return coordinates.getR();
    }

    /**
     * Getter for the {@code Coordinates} of this {@code Square}.
     * @return {@code Coordinates} of this {@code Square}
     */
    public Coordinates getCoordinates() {
        return coordinates.copy();
    }

    /**
     * Returns the number of blocks on this {@code Square}.
     *
     * @return the number of blocks on this {@code Square}
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns a list of all the pieces in this {@code Square}, in order. Workers present on this {@code Square} are
     * copied by calling {@code Worker}'s {@code copy} method.
     * @return an ordered list of all the pieces in this {@code Square}
     */
    public ArrayList<Piece> getPieceStack() {
        Piece[] piecesToCopy = pieceStack.toArray(new Piece[0]);
        ArrayList<Piece> arrayList = new ArrayList<Piece>(Arrays.asList(piecesToCopy));
        if (!arrayList.isEmpty()) {
            Piece top = arrayList.get(arrayList.size() - 1);
            if (top instanceof Worker) {
                arrayList.set(arrayList.size() - 1, ((Worker) top).copy());
            }
        }
        return arrayList;
    }

    /**
     * Returns the {@code Piece} that was last inserted in this {@code Square}.
     * @return the {@code Piece} that was last inserted in this {@code Square}
     */
    public Piece getTop() {
        return (pieceStack.isEmpty()) ? null : pieceStack.peek();
    }

    /**
     * Returns true if this {@code Square} does not contain a {@code Dome} or {@code Worker}.
     * @return true if this {@code Square} does not contain a {@code Dome} or {@code Worker}
     */
    public boolean isFree() {
        return getTop() == null || getTop() instanceof Block;
    }

    /**
     * Inserts {@code piece} on top of this {@code Square}.
     * @param piece {@code piece} to be inserted
     */
    public void insert(Piece piece) {
        pieceStack.push(piece);
        if (piece instanceof Block) {
            height++;
        }
    }

    /**
     * Removes the {@code piece} on top of this {@code Square}.
     */
    public void removeTop() {
        if (!pieceStack.isEmpty()) {
            Piece top = pieceStack.peek();
            if (top instanceof Block)
                height--;
            pieceStack.pop();
        }
    }

    /**
     * Returns true if this {@code Square} is adjacent to {@code s}.
     * @param s {@code Square} that is being examined
     * @return true if this {@code Square} is adjacent to {@code s}
     */
    public boolean isAdjacent(Square s) {
        int dx = abs(coordinates.getR() - s.getR());
        int dy = abs(coordinates.getC() - s.getC());
        return (dx <= 1)
                && (dy <= 1)
                && (dx != 0 || dy != 0); //checks that s is not being compared to itself
    }

    /**
     * Returns true if this {@code Square} is adjacent to the {@code Square} identified by {@code c}.
     * @param c Coordinates corresponding to the {@code Square} that is being examined
     * @return true if this {@code Square} is adjacent to the {@code Square} identified by {@code c}
     */
    public boolean isAdjacent(Coordinates c) {
        int dx = abs(coordinates.getR() - c.getR());
        int dy = abs(coordinates.getC() - c.getC());
        return (dx <= 1)
                && (dy <= 1)
                && (dx != 0 || dy != 0); //checks that s is not being compared to itself
    }

    /**
     * Returns a copy of this {@code Square}. The copy has the same position and an indentical {@code pieceStack}, with
     * the only difference being that any {@code Worker}s present in the original are copies instead of the same reference.
     * @return Returns a copy of this {@code Square}
     */
    public Square copy() {
        Square copy = new Square(coordinates);
        ArrayList<Piece> piecesToCopy = this.getPieceStack();
        for (Piece piece : piecesToCopy) {
            copy.insert(piece);
        }
        return copy;
    }

    /**
     * Returns a {@code ReducedSquare} corresponding to this Square.
     * @return a {@code ReducedSquare} corresponding to this Square
     */
    public ReducedSquare reduce() {
        return new ReducedSquare(this);
    }

    /**
     * Returns true if this {@code Square} is on the perimeter of the board.
     * @return true if this {@code Square} is on the perimeter of the board
     */
    public boolean isPerimetral() {
        return coordinates.getR() == 0
                || coordinates.getR() == 4
                || coordinates.getC() == 0
                || coordinates.getC() == 4;
    }
}
