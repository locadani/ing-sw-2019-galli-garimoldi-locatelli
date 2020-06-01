package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.commons.ReducedSquare;

import java.util.List;

//TODO classe astratta per attributi comuni
public interface UInterface {
    void getNPlayers();
    String getPlayerInfo();
    void placeWorker();
    void performAction();
    void chooseColour(List<String> availableColors);
    String getConnectionInfo();
    void startMatch();
    void choose2Divinities(List<String> availableDivinities);
    void choose3Divinities(List<String> availableDivinities);
    void updateBoard(List<ReducedSquare> changedSquares);
    void pickDivinity(List<String> divinitiesList);
}
