package it.polimi.ingswPSP35.client.gui.Daniele;

import it.polimi.ingswPSP35.client.MatchInfo;
import it.polimi.ingswPSP35.client.ServerHandler;
import it.polimi.ingswPSP35.client.gui.Request;
import it.polimi.ingswPSP35.server.controller.Match;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatchFrame extends JFrame {

    private BoardPanel boardPanel;
    private ActionPanel actionPanel;
    private Container container;
    private ServerHandler sh;
    private Request request;
    private MatchInfo matchInfo;

    public MatchFrame(ServerHandler sh, MatchInfo matchInfo) {

        this.matchInfo = matchInfo;
        this.sh = sh;

        request = new Request();
        boardPanel = new BoardPanel(request);
        actionPanel = new ActionPanel(request, sh, matchInfo);
        container = getContentPane();

        this.setSize(600, 500);
        container.add(boardPanel, BorderLayout.NORTH);
        container.add(actionPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public void placeWorkers() {
        disableButtonsPanel();
        setVisible(true);
        actionPanel.placeWorker();
    }

    public void startMatch()
    {
        actionPanel.startMatch();
    }

    public void disableButtonsPanel() {

        actionPanel.disableButtonsPanel();
    }

    public void enableButtonsPanel() {

        actionPanel.enableButtonsPanel();
    }

    public void updateCell(int cell, int height, String piece, int colour) {
        boardPanel.updateCell(cell, height, piece, colour);
    }

    private void removeActionListeners(JButton button)
    {
        for(ActionListener listener : button.getActionListeners())
        {
            button.removeActionListener(listener);
        }
    }

    public void startTurn()
    {
        request.reset();
        //JOptionPane.showMessageDialog(this, "Your turn started");
    }
}
