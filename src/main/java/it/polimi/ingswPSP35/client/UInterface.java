package it.polimi.ingswPSP35.client;

import java.util.List;

//TODO classe astratta per attributi comuni
public interface UInterface {
    void getNPlayers();
    void getDivinities(int numberofplayers);
    void getPlayerInfo();
    void chooseDivinity(List<String> divinitiesList);
    void getPosition();
    void performAction();
    void chooseColour(List<String> availableColors);
    String getConnectionInfo();
    void update(String[] params);
    void startMatch();
    void configUI(ServerHandler serverHandler);
}
