package it.polimi.ingswPSP35.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellButtonListener implements ActionListener
{
    private Request request;
    private Component parent;
    public CellButtonListener(Component parent, Request request)
    {
        this.request = request;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        request.setCell(Integer.parseInt(e.getActionCommand()));
        request.print();
    }
}