package it.polimi.ingswPSP35.client;

import java.util.List;

public interface UInterface {
    public int getNPlayers();
    public List<String> getDivinities(int numberofplayers);
    public String[] getPlayerInfo();
    public String chooseDivinity(List<String> divinitiesList);
    public int getPosition();
    public String performAction();
}
