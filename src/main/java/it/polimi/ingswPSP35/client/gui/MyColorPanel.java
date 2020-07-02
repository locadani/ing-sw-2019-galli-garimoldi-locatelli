package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.net.PortUnreachableException;

public class MyColorPanel extends JPanel {

    private CardLayout cardLayout = new CardLayout();

    public MyColorPanel(){

        this.setOpaque(false);
        this.setLayout(cardLayout);

        System.out.println("prima blu");
        JLabel blue = new JLabel(new ImageIcon(getClass().getResource("/blue.png")));
        this.add(blue, "2");
        System.out.println("prima verde");

        JLabel green = new JLabel(new ImageIcon(getClass().getResource("/green.png")));
        this.add(green, "1");
        System.out.println("prima rosso");

        JLabel red = new JLabel(new ImageIcon(getClass().getResource("/red.png")));
        this.add(red, "0");

    }

    public void switchColor(int color) {
        cardLayout.show(this, Integer.toString(color));
    }
}
