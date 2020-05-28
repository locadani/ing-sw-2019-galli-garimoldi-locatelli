package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.Info;
import it.polimi.ingswPSP35.client.ServerHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigWindow extends JFrame {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private static ConfigWindow test;

    //private List<String> testList = new ArrayList<>(List.of("athena", "atlas", "pan"));

    private Login login;
    private Connection connection;
    private ColorChooser colorChooser;
    private SelectDivinities  selectDivinities;
    private SelectNumberOfPlayers selectNumberOfPlayers;
    private ChooseDivinities chooseDivinities;

    private ImageIcon image = new ImageIcon(getClass().getResource("/santorini.png"));
    private ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));
    private Image scaledImg = image.getImage().getScaledInstance(640, 640, Image.SCALE_SMOOTH);
    private JLabel background = new JLabel(new ImageIcon(scaledImg));
    private CardLayout cardLayout = new CardLayout();
    private ServerHandler serverHandler;

    public ConfigWindow(ServerHandler serverHandler) {

        this.serverHandler = serverHandler;
        login = new Login(serverHandler);
        colorChooser = new ColorChooser(serverHandler);
        connection = new Connection(serverHandler);
        selectNumberOfPlayers = new SelectNumberOfPlayers(serverHandler);
        this.setSize(LARG, ALT);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Santorini Welcome");
        this.setIconImage(icon.getImage());

        background.setLayout(cardLayout);

        JPanel waitingPanel = new JPanel();
        waitingPanel.setOpaque(false);

        background.add(waitingPanel, "0");
        background.add(connection, "1");
        background.add(selectNumberOfPlayers, "2");
        background.add(login, "3");
        background.add(colorChooser, "6");

        cardLayout.show(background, "0");
        add(background);

        this.setVisible(true);
    }

    public void setLoginPanel() {
        cardLayout.show(background, "3");

    }

    public void setConnectionPanel(){

        cardLayout.show(background, "1");

    }

    public void setColorChooserPanel(){
        cardLayout.show(background, "6");

    }

    public void setSelectNumberOfPlayersPanel(){
        cardLayout.show(background, "2");
    }

    public void setSelectDivinitiesPanel(int nPlayers){
        selectDivinities = new SelectDivinities(nPlayers, serverHandler);

        background.add(selectDivinities, "4");
        cardLayout.show(background, "4");
    }

    public void setChooseDivinitiesPanel(List<String> divinities){
        chooseDivinities = new ChooseDivinities(divinities, serverHandler);
        background.add(chooseDivinities, "5");
        cardLayout.show(background, "5");
    }



    public List<String> getChosenDivinties(){

        List<String> divinities = selectDivinities.getChosenDivinities();
        return divinities;
    }
}

