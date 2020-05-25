package it.polimi.ingswPSP35.client;

import java.util.List;

public interface UInterface {
    void getNPlayers();
    void chooseDivinities(int numberofplayers, List<String> allDivinities);
    void getPlayerInfo();
    void pickDivinity(List<String> divinitiesList);
    void getPosition();
    void performAction();
    void chooseColour(List<String> availableColors);
    void getConnectionInfo();
    void displayNotification(String notification);
}