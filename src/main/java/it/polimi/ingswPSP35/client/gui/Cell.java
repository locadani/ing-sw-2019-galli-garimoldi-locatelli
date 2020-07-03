package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;

public class Cell extends JButton {

    public Cell(String name) {
        this.setIcon(null);
        setActionCommand(name);
        this.setContentAreaFilled(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
    }

    public void update(String object, int colour) {
        if (!object.equals("E"))
            this.setIcon(ImageHandler.getImage(object, colour));
        else
            this.setIcon(null);
    }
}