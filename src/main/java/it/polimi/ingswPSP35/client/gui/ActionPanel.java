package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel
{
    public ActionPanel(Request request)
    {
        ActionButton moveButton = new ActionButton("Move");
        ActionButton buildButton = new ActionButton("Build");
        ActionButton clearButton = new ActionButton("Clear");
        ActionButton placeWorkerButton = new ActionButton("Place Worker");
        ActionButton submitButton = new ActionButton("Submit");

        moveButton.addActionListener(new ActionButtonListener(moveButton, request));
        buildButton.addActionListener(new ActionButtonListener(buildButton, request));
        clearButton.addActionListener(new ActionButtonListener(clearButton, request));
        placeWorkerButton.addActionListener(new ActionButtonListener(placeWorkerButton, request));
        submitButton.addActionListener(new ActionButtonListener(submitButton, request));

        this.add(moveButton);
        this.add(buildButton);
        this.add(clearButton);
        this.add(placeWorkerButton);
    }
}