package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.commons.Action;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionButtonListener implements ActionListener
{
    private Request request;
    private JButton button;

    public ActionButtonListener(JButton button, Request request)
    {
        this.request = request;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        request.setAction(Action.valueOf(e.getActionCommand().toUpperCase()));
        request.print();
   }
}