package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.commons.ReducedSquare;

import java.util.List;
import java.util.Map;

/**
 *  Allows interface between user and game
 */
public interface UInterface {

    /**
     * Asks the first player to choose the number of players for the next game
     */
    void getNPlayers();

    /**
     * Asks the player for the username
     * @return username typed by the player
     */
    String getPlayerInfo();

    /**
     * Asks the player to place the workers at the beginning of the game
     */
    void placeWorker();

    /**
     * Manages a player's turn
     */
    void performAction();

    /**
     * Asks the player to choose a colour to use for the next game
     *
     * @param availableColors the list of colors still available to select
     */
    void chooseColour(List<String> availableColors);

    /**
     * Asks the player for the ip address.
     *
     * @return ip address typed by the user
     */
    String getConnectionInfo();

    /**
     * stores all the current match parameters
     * @param userToDivinity list of every player and their respective divinity
     */
    void setMatchInfo(Map<String, String> userToDivinity);

    /**
     * Asks the first player to choose 2 divinities in a 2 players match
     * @param availableDivinities the list of all the game divinities
     */
    void choose2Divinities(List<String> availableDivinities);

    /**
     * Asks the first player to choose 3 divinities in a 3 players match
     * @param availableDivinities the list of all the game divinities
     */
    void choose3Divinities(List<String> availableDivinities);

    /**
     * Updates the board during the game
     * @param changedSquares the changed cells
     */
    void updateBoard(List<ReducedSquare> changedSquares);

    /**
     *  Asks the player to choose a divinity for the next game
     * @param divinitiesList the list of available divinities for the next game
     */
    void pickDivinity(List<String> divinitiesList);

    /**
     * It displays all the notifications during the game to the players
     * @param notification the message sent to the player
     */
    void displayNotification(String notification);

    /**
     * at the end of the configuration phase, it starts the match
     */
    void startMatch();

    /**
     * Asks the player to choose the first player
     * among the ones provided
     * @param players possible first players
     */
    void chooseFirstPlayer(List<String> players);

    /**
     * Notifies the player that his turn is ended
     */
    void turnEnded();

    /**
     * Notifies the player about every chosen color
     * @param chosenColors colors chosen by the players
     */
    void chosenColors(Map<String, String> chosenColors);
}
