package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.UInterface;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Gui implements UInterface {

    private ConfigWindow configWindow;

    public Gui() {

        this.configWindow = new ConfigWindow();
    }

    @Override
    public int getNPlayers() {

        AtomicInteger nplayers = new AtomicInteger();
        int numberofplayers;

        configWindow.setSelectNumberOfPlayersPanel();

        do {

            nplayers.set(configWindow.getNumberofPlayers());
            numberofplayers = nplayers.get();

        } while (numberofplayers == 0);


        return numberofplayers;
    }

    @Override
    public List<String> getDivinities(int numberofplayers) {

        configWindow.setSelectDivinitiesPanel(numberofplayers);

        return null;
    }

    @Override
    public String[] getPlayerInfo() {

        AtomicReference<String[]> infos = new AtomicReference<>();
        String[] playerInfo;

        configWindow.setLoginPanel();

        do {

            infos.set(configWindow.getPlayerInfos());
            playerInfo = infos.get();

        } while (playerInfo[0] == null || playerInfo[1] == null);

        return playerInfo;
    }

    @Override
    public String chooseDivinity(List<String> divinitiesList) {

        AtomicReference<String> atomicDivinity = new AtomicReference<>();

        String divinity;

        configWindow.setChooseDivinitiesPanel(divinitiesList);

        do {

            atomicDivinity.set(configWindow.getChosenDivinity());
            divinity = atomicDivinity.get();

        } while (divinity == null);

        return divinity;
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

        AtomicReference<String> atomiColour = new AtomicReference<>();

        String chosenColour;

        configWindow.setColorChooserPanel();

        do {
            atomiColour.set(configWindow.getChosenColor());
            chosenColour = atomiColour.get();
        } while (chosenColour == null);

        return chosenColour;
    }

    @Override
    public String getConnectionInfo() {

        AtomicReference<String> ipaddress = new AtomicReference<>();
        AtomicReference<String> portnumber = new AtomicReference<>();

        String ip, port, connectionInfo;

        configWindow.setConnectionPanel();

        do {

            ipaddress.set(configWindow.getIpAddress());
            portnumber.set(configWindow.getPortNumber());
            ip = ipaddress.get();
            port = portnumber.get();

        } while (ip == null || port == null);

        connectionInfo = ip + ":" + port;

        return connectionInfo;
    }
}
