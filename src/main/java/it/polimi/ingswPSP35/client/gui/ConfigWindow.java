package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigWindow extends JFrame {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private static ConfigWindow test;
    private Login login = new Login();
    private Connection connection = new Connection();
    private ColorChooser colorChooser = new ColorChooser();
    private SelectDivinities selectDivinities;
    private SelectNumberOfPlayers selectNumberOfPlayers = new SelectNumberOfPlayers();

    private ImageIcon image = new ImageIcon(getClass().getResource("/santorini.png"));
    private Image scaledImg = image.getImage().getScaledInstance(640, 640, Image.SCALE_SMOOTH);
    private JLabel background = new JLabel(new ImageIcon(scaledImg));


    public ConfigWindow() {

        this.setSize(LARG, ALT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Santorini Welcome");


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

    public static void test(){

        test = new ConfigWindow();
    }

    public String getIpAddress(){

        String ipaddress = connection.getIp();

        return ipaddress;
    }

    public int getNumberofPlayers(){

        int numberofplayers = selectNumberOfPlayers.getNumberofplayers();
        return numberofplayers;

    }

    public String getPortNumber(){

        String portnumber = connection.getPort();
        return portnumber;
    }

    


}
