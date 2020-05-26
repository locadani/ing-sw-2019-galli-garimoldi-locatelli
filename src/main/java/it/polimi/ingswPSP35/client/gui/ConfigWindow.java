package it.polimi.ingswPSP35.client.gui;

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

    private Login login = new Login();
    private Connection connection = new Connection();
    private ColorChooser colorChooser = new ColorChooser();
    private SelectDivinities  selectDivinities;
    private SelectNumberOfPlayers selectNumberOfPlayers = new SelectNumberOfPlayers();
    private ChooseDivinities chooseDivinities;

    private ImageIcon image = new ImageIcon(getClass().getResource("/santorini.png"));
    private ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));
    private Image scaledImg = image.getImage().getScaledInstance(640, 640, Image.SCALE_SMOOTH);
    private JLabel background = new JLabel(new ImageIcon(scaledImg));
    private CardLayout cardLayout = new CardLayout();


    public ConfigWindow() {

        this.setSize(LARG, ALT);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        //background.add(login);
        cardLayout.show(background, "3");
        //login.setVisible(true);

    }

    public void setConnectionPanel(){

        cardLayout.show(background, "1");
        //background.add(connection);
        //connection.setVisible(true);

    }

    public void setColorChooserPanel(){

        //background.add(colorChooser);
        cardLayout.show(background, "6");
        //colorChooser.setVisible(true);

    }

    public void setSelectNumberOfPlayersPanel(){

        //background.add(selectNumberOfPlayers);
        cardLayout.show(background, "2");
        //selectNumberOfPlayers.setVisible(true);

    }

    public void setSelectDivinitiesPanel(int nPlayers){

        selectDivinities = new SelectDivinities(nPlayers);

        background.add(selectDivinities, "4");
        cardLayout.show(background, "4");
        //background.add(selectDivinities);
        //selectDivinities.setVisible(true);
    }

    public void setChooseDivinitiesPanel(List<String> divinities){
        chooseDivinities = new ChooseDivinities(divinities);
        background.add(chooseDivinities, "5");
        cardLayout.show(background, "5");
        //background.add(chooseDivinities);
        //chooseDivinities.setVisible(true);
    }

    public static void test(){

        test = new ConfigWindow();
    }

    public String getIpAddress(){

        String ipaddress = connection.getIp();

        

        return ipaddress;
    }

    public String getPortNumber(){

        String portnumber = connection.getPort();
        return portnumber;
    }

    public int getNumberofPlayers(){

        int numberofplayers = selectNumberOfPlayers.getNumberofplayers();
        return numberofplayers;

    }

    public String[] getPlayerInfos(){

        String[] playerInfo = login.getPlayerInfo();
        return playerInfo;
    }

    public String getChosenColor(){

        String chosenColor = colorChooser.getColor();
        return chosenColor;
    }

    public String getChosenDivinity(){

        String divinity = chooseDivinities.getPickedDivinity();
        return divinity;
    }

    public List<String> getChosenDivinties(){

        List<String> divinities = selectDivinities.getChosenDivinities();
        return divinities;
    }


    public static void main(String[] args) {

        AtomicReference<String> test01 = new AtomicReference<>();
        AtomicReference<String> test02 = new AtomicReference<>();
        AtomicReference<String[]> infos = new AtomicReference<>();
        AtomicReference<String> atomiColour = new AtomicReference<>();
        AtomicReference<List<String>> atomicDivinities = new AtomicReference<>();
        List<String> divinities;
        String[] playerInfo;

        AtomicInteger nplayers = new AtomicInteger();

        int numberofplayers;
        String ip, port;
        String chosenColour;


        List<String> div = new ArrayList<>(List.of("prometheus", "artemis", "demeter"));


        test();
        test.setConnectionPanel();



        do {
            test01.set(test.getIpAddress());
            test02.set(test.getPortNumber());
            ip = test01.get();
            port = test02.get();
            System.out.println("test");
        } while (ip == null || port == null);
        System.out.println(ip+port);



        test.setSelectNumberOfPlayersPanel();

        do {

            nplayers.set(test.getNumberofPlayers());
            numberofplayers = nplayers.get();

        } while (numberofplayers == 0);



        test.setLoginPanel();

        do {

            infos.set(test.getPlayerInfos());
            playerInfo = infos.get();

        } while (playerInfo[0] == null || playerInfo[1] == null);


        test.setColorChooserPanel();

        do {
            atomiColour.set(test.getChosenColor());
            chosenColour = atomiColour.get();
        } while (chosenColour == null);



        test.setSelectDivinitiesPanel(numberofplayers);

        do {
            atomicDivinities.set(test.getChosenDivinties());
            divinities = atomicDivinities.get();
        } while (divinities.size() < numberofplayers);

        AtomicReference<String> atomicDivinity = new AtomicReference<>();

        String divinity;

        test.setChooseDivinitiesPanel(div);

        do {

            atomicDivinity.set(test.getChosenDivinity());
            divinity = atomicDivinity.get();

        } while (divinity == null);


    }




}

