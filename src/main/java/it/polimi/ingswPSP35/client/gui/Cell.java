package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;

public class Cell extends JButton {

    private Request request;
    public Cell(String name, Request request)
    {
        this.request = request;
        this.setIcon(ImageHandler.getImage("empty"));
        this.setText(name);
        this.setContentAreaFilled(false);
    }

    public void update(String object)
    {
        this.setIcon(ImageHandler.getImage(object));
    }
}