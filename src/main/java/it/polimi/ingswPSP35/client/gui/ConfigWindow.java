package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.MatchInfo;
import it.polimi.ingswPSP35.client.NetworkHandler;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ConfigWindow extends JFrame {

    private static final int LARG = 640;
    private static final int ALT = 640;

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
    private NetworkHandler networkHandler;
    private MatchInfo matchInfo;

    public ConfigWindow(NetworkHandler networkHandler, MatchInfo matchInfo) {

        this.matchInfo = matchInfo;
        this.networkHandler = networkHandler;
        selectNumberOfPlayers = new SelectNumberOfPlayers(networkHandler);
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
        background.add(selectNumberOfPlayers, "2");

        cardLayout.show(background, "0");
        add(background);

        this.setVisible(true);
    }

    public void setLoginPanel(LinkedBlockingQueue<String> input) {
        login = new Login(networkHandler, matchInfo, input);
        background.add(login, "3");
        cardLayout.show(background, "3");
    }

    public void setConnectionPanel(LinkedBlockingQueue<String> input){

        connection = new Connection(networkHandler, input);
        background.add(connection, "1");
        cardLayout.show(background, "1");

    }

    public void setColorChooserPanel(List<String> availableColors){
        colorChooser = new ColorChooser(networkHandler, matchInfo, availableColors);
        background.add(colorChooser, "6");
        cardLayout.show(background, "6");

    }

    public void setSelectNumberOfPlayersPanel(){
        cardLayout.show(background, "2");
    }

    public void setSelectDivinitiesPanel(int nPlayers, List<String> allDivinities){
        selectDivinities = new SelectDivinities(nPlayers, networkHandler, allDivinities);

        background.add(selectDivinities, "4");
        cardLayout.show(background, "4");
    }

    public void setChooseDivinitiesPanel(List<String> divinities){
        chooseDivinities = new ChooseDivinities(divinities, networkHandler, matchInfo);
        background.add(chooseDivinities, "5");
        cardLayout.show(background, "5");
    }
}

