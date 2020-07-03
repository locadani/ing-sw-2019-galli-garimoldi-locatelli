package it.polimi.ingswPSP35.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellButtonListener implements ActionListener
{
    private Request request;

    public CellButtonListener(Request request)
    {
        this.request = request;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        request.setCell(Integer.parseInt(e.getActionCommand()));
    }
}