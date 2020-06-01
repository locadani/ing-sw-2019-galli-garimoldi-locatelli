package it.polimi.ingswPSP35.client.gui.Daniele;

import it.polimi.ingswPSP35.client.MatchInfo;
import it.polimi.ingswPSP35.client.NetworkHandler;
import it.polimi.ingswPSP35.client.gui.ActionButtonListener;
import it.polimi.ingswPSP35.client.gui.Request;
import it.polimi.ingswPSP35.commons.MessageID;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel
{
    private NetworkHandler sh;
    private Request request;

    private ActionButton moveButton = new ActionButton("Move");
    private ActionButton buildButton = new ActionButton("Build");
    private ActionButton godPowerButton = new ActionButton("GodPower");
    private ActionButton endTurnButton = new ActionButton("Endturn");
    private ActionButton nextButton = new ActionButton("Next");
    private ActionButton matchInfoButton = new ActionButton("Match Info");

    private MatchInfo matchInfo;

    public ActionPanel(Request request, NetworkHandler sh, MatchInfo matchInfo)
    {
        this.matchInfo = matchInfo;
        this.sh = sh;
        this.request = request;
        JPanel panel = this;

        moveButton.addActionListener(new ActionButtonListener(moveButton, request));
        buildButton.addActionListener(new ActionButtonListener(buildButton, request));
        godPowerButton.addActionListener(new ActionButtonListener(godPowerButton, request));
        endTurnButton.addActionListener(new ActionButtonListener(endTurnButton, request));
        matchInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(panel.getParent(),matchInfo.print());
            }
        });

        this.add(moveButton);
        this.add(buildButton);
        this.add(godPowerButton);
        this.add(endTurnButton);
        this.add(nextButton);
        this.add(matchInfoButton);

    }

    public void placeWorker()
    {
        removeActionListeners(nextButton);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(request.getWorker() != 0) {
                    sh.send(MessageID.PLACEWORKER, Integer.toString(request.getWorker()));
                }
            }
        });
    }

    public void startMatch()
    {
        removeActionListeners(nextButton);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(request.isReady())
                {
                    sh.send(MessageID.PERFORMACTION, request.getInfo());
                }
            }
        });
        enableButtonsPanel();
    }

    private void removeActionListeners(JButton button)
    {
        for(ActionListener listener : button.getActionListeners())
        {
            button.removeActionListener(listener);
        }
    }

    public void disableButtonsPanel() {

        moveButton.setEnabled(false);
        buildButton.setEnabled(false);
        godPowerButton.setEnabled(false);
        endTurnButton.setEnabled(false);

    }

    public void enableButtonsPanel() {

        moveButton.setEnabled(true);
        buildButton.setEnabled(true);
        godPowerButton.setEnabled(true);
        endTurnButton.setEnabled(true);

    }
}