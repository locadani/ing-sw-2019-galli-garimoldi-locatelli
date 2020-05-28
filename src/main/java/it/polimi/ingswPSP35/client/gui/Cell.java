package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;

public class Cell extends JButton {

    public Cell(String name)
    {
       // this.setIcon(ImageHandler.getImage("empty"));
        this.setText(name);
        this.setContentAreaFilled(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
    }

    public void update(String object)
    {
        this.setIcon(ImageHandler.getImage(object));
    }
}