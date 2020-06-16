package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.net.PortUnreachableException;

public class MyColorPanel extends JPanel {

    private CardLayout cardLayout = new CardLayout();

    public MyColorPanel(){

        this.setOpaque(false);
        this.setLayout(cardLayout);

        JLabel blue = new JLabel(new ImageIcon(getClass().getResource("/blue.png")));
        this.add(blue, "2");

        JLabel green = new JLabel(new ImageIcon(getClass().getResource("/green.png")));
        this.add(green, "1");

        JLabel red = new JLabel(new ImageIcon(getClass().getResource("/red.png")));
        this.add(red, "0");

    }

    public void switchColor(int color) {
        cardLayout.show(this, Integer.toString(color));
    }
}
