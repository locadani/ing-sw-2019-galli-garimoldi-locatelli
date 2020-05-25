package it.polimi.ingswPSP35.client;

import java.util.List;

public interface UInterface {
    int getNPlayers();
    List<String> getDivinities(int numberofplayers);
    String[] getPlayerInfo();
    String chooseDivinity(List<String> divinitiesList);
    int getPosition();
    String performAction();
    String chooseColour(List<String> availableColors);
    String getConnectionInfo();
    void notify(String message);
}
