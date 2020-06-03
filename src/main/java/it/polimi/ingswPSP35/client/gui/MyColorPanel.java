package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.net.PortUnreachableException;

public class MyColorPanel extends JPanel {

    private String color;
    private CardLayout cardLayout = new CardLayout();

    public MyColorPanel(String color){

        this.color = color;

        this.setOpaque(false);
        this.setLayout(cardLayout);

        JLabel blue = new JLabel(new ImageIcon(getClass().getResource("/blue.png")));
        this.add(blue, "BLUE");

        JLabel green = new JLabel(new ImageIcon(getClass().getResource("/green.png")));
        this.add(green, "GREEN");

        JLabel red = new JLabel(new ImageIcon(getClass().getResource("/red.png")));
        this.add(red, "RED");

        cardLayout.show(this, color);
    }
}
