package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;

public class ActionButton extends JButton
{
    private boolean clicked;

    public ActionButton(String name)
    {
        this.setText(name);
        clicked = false;
    }
}