package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.UInterface;

import java.util.List;

public class Gui implements UInterface {
    @Override
    public int getNPlayers() {
        int numberofplayers;

        SelectNumberOfPlayers selectNumberOfPlayers = new SelectNumberOfPlayers();

        numberofplayers = selectNumberOfPlayers.getNumberofplayers();

        return numberofplayers;
    }

    @Override
    public List<String> getDivinities(int numberofplayers) {
        return null;
    }

    @Override
    public String[] getPlayerInfo() {
        String[] playerinfo;

        Login login = new Login();

       playerinfo =  login.getPlayerinfo();

        return playerinfo;
    }

    @Override
    public String chooseDivinity(List<String> divinitiesList) {
        return null;
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public String performAction() {
        return null;
    }

    @Override
    public String chooseColour(List<String> availableColors) {
        return null;
    }

    @Override
    public String getConnectionInfo() {
        return null;
    }
}
