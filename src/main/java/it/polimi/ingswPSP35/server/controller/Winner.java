package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.controller.divinities.Divinity;

/**
 * Class used to handle victory. Whenever a {@code Divinity} has established it has won, it calls {@code setWinner} method,
 * passing itself as a parameter. This change is then noticed by {@code GameDirector}, which handles the details of victory.
 *
 * @author Paolo Galli
 *
 * @see Divinity
 */
public class Winner {
    private Divinity winner;

    /**
     * Sole constructor. Called during setup by {@code GameDirector}, to be then passed to all Divinities.
     */
    public Winner()
    {
        winner = null;
    }

    /**
     * Getter for {@code winner} parameter.
     * @return winner parameter
     */
    public Divinity getWinner() {
        return winner;
    }

    /**
     * Setter for {@code winner} parameter. Only called once per game by the {@code Divinity} that has won.
     * @param winner {@code Divinity} to be set as {@code winner}
     */
    public void setWinner(Divinity winner) {
        this.winner = winner;
    }

}
