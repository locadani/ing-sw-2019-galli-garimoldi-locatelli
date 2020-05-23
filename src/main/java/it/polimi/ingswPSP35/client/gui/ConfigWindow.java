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

    private List<String> testList = new ArrayList<>(List.of("athena", "atlas", "pan"));

    private Login login = new Login();
    private Connection connection = new Connection();
    private ColorChooser colorChooser = new ColorChooser();
    private SelectDivinities  selectDivinities = new SelectDivinities(2);
    private SelectNumberOfPlayers selectNumberOfPlayers = new SelectNumberOfPlayers();
    private ChooseDivinities chooseDivinities = new ChooseDivinities(testList);

    private ImageIcon image = new ImageIcon(getClass().getResource("/santorini.png"));
    private ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));
    private Image scaledImg = image.getImage().getScaledInstance(640, 640, Image.SCALE_SMOOTH);
    private JLabel background = new JLabel(new ImageIcon(scaledImg));


    public ConfigWindow() {

        this.setSize(LARG, ALT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Santorini Welcome");
        this.setIconImage(icon.getImage());


        background.setLayout(new BorderLayout());
        add(background);

        this.setVisible(true);
    }

    public void setLoginPanel() {

        background.add(login);
        login.setVisible(true);

    }

    public void setConnectionPanel(){

        background.add(connection);
        connection.setVisible(true);

    }

    public void setColorChooserPanel(){

        background.add(colorChooser);
        colorChooser.setVisible(true);

    }

    public void setSelectNumberOfPlayersPanel(){

        background.add(selectNumberOfPlayers);
        selectNumberOfPlayers.setVisible(true);

    }

    public void setSelectDivinitiesPanel(int nPlayers){

        selectDivinities = new SelectDivinities(nPlayers);

        background.add(selectDivinities);
        selectDivinities.setVisible(true);
    }

    public void setChooseDivinitiesPanel(List<String> divinities){
        chooseDivinities = new ChooseDivinities(divinities);

        background.add(chooseDivinities);
        chooseDivinities.setVisible(true);
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


    public static void main(String[] args){

        AtomicReference<List<String>> test01 = new AtomicReference<>();

        List<String> divinity;
        int numberOfPlayers = 3;

        List<String> div = new ArrayList<>(List.of("prometheus", "artemis", "demeter"));


        test();
        test.setSelectDivinitiesPanel(numberOfPlayers);



        do {
           test01.set(test.getChosenDivinties());
            divinity = test01.get();
            System.out.println("test");
        } while (divinity.size() < numberOfPlayers);
        System.out.println(divinity);

    }


}
