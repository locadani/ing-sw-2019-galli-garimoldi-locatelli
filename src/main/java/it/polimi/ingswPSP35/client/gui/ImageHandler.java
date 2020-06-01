package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;

public class ImageHandler {

    public static ImageIcon getImage(String name)
    {
        name = name.toUpperCase();
        ImageIcon icon = new ImageIcon(ImageHandler.class.getResource("/img/" + name + ".png"));
        return icon;
    }
}