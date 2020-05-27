package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.commons.ReducedSquare;

import java.util.List;

public interface UInterface {
    void getNPlayers();
    void choose2Divinities(List<String> allDivinities);
    void choose3Divinities(List<String> allDivinities);
    String getPlayerInfo();
    void pickDivinity(List<String> divinitiesList);
    void placeWorker();
    void performAction();
    void chooseColour(List<String> availableColors);
    String getConnectionInfo();
    void displayNotification(String notification);
    void updateBoard(List<ReducedSquare> changedSquares);
}