package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.commons.Coordinates;

/**
 * Class representing workers. It has indirect information about its position on the {@code Board} and a copy
 * method to allow {@code DefeatChecker} to operate properly.
 *
 * @author Paolo Galli
 *
 * @see it.polimi.ingswPSP35.server.controller.DefeatChecker
 */
public class Worker implements Piece {
    private Coordinates coordinates;

    private final Player player;

    /**
     * Generates a {@code Worker} in position identified by {@code Coordinates}, belonging to {@code player}.
     *
     * @param coordinates {@code Coordinates} of the position of this worker
     * @param player owner of this worker
     */
    public Worker(Coordinates coordinates, Player player){
        this.coordinates = coordinates;
        this.player = player;
    }

    /**
     * Getter for the {@code Coordinates} of this {@code Worker}.
     * @return {@code Coordinates} of this {@code Worker}
     */
    public Coordinates getCoordinates(){ return coordinates;}

    /**
     * Getter for the row number of this {@code Worker}.
     * @return row number of this {@code Worker}
     */
    public int getR() {
        return coordinates.getR();
    }

    /**
     * Getter for the column number of this {@code Worker}.
     * @return column number of this {@code Worker}
     */
    public int getC() {
        return coordinates.getC();
    }

    /**
     * Setter for the {@code Coordinates} of this {@code Worker}.
     * @param coordinates {@code Coordinates} to be set
     */
    public void setCoordinates(Coordinates coordinates) {this.coordinates = coordinates;}


    /**
     * Getter for this worker's {@code Player}.
     * @return this worker's {@code Player}
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Generate a copy of this {@code Worker}.
     * @return a copy of this {@code Worker}
     */
    public Worker copy() {
        return new Worker(this.coordinates, this.getPlayer());
    }

}
