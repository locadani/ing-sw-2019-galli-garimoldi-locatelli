package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.UInterface;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Gui implements UInterface {

    private ConfigWindow configWindow;
    private Login login = new Login();
    private Connection connection = new Connection();

    public Gui(){

        this.configWindow = new ConfigWindow();
    }

    @Override
    public int getNPlayers() {
        int numberofplayers;
        AtomicInteger nplayers = new AtomicInteger();

       configWindow.setSelectNumberOfPlayersPanel();

       do {
           nplayers.set(configWindow.getNumberofPlayers());
           numberofplayers = nplayers.get();
       }while (numberofplayers == 0);


        return numberofplayers;
    }

    @Override
    public List<String> getDivinities(int numberofplayers) {

        configWindow.setSelectDivinitiesPanel(numberofplayers);

        return null;
    }

    @Override
    public String[] getPlayerInfo() {
        String[] playerinfo;

        configWindow.setLoginPanel();

        return null;
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

        configWindow.setColorChooserPanel();

        return null;
    }

    @Override
    public String getConnectionInfo() {
        AtomicReference<String> ipaddress = new AtomicReference<>();
        AtomicReference<String> portnumber = new AtomicReference<>();

        String ip, port, connectionInfo;

        configWindow.setConnectionPanel();

        do {ipaddress.set(configWindow.getIpAddress());
            portnumber.set(configWindow.getPortNumber());
            ip =ipaddress.get();
            port = portnumber.get();
        }while (ip == null && port == null);

        connectionInfo = ip + ":" + port;

        return connectionInfo;
    }
}
