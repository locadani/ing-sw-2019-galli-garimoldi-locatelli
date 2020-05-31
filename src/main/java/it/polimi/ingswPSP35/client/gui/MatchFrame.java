package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;

public class MatchFrame extends JFrame {

    private BoardPanel boardPanel;
    private JPanel actionPanel;

    private Container container;

    public MatchFrame(JPanel actionPanel, BoardPanel boardPanel) {

        container = getContentPane();

        this.actionPanel = actionPanel;
        this.boardPanel = boardPanel;

        this.setSize(600, 500);
       /* JSplitPane splitter = new JSplitPane(SwingConstants.HORIZONTAL, boardPanel, actionPanel);
        splitter.setSize(this.getSize());
        splitter.setContinuousLayout(true);
        splitter.setDividerLocation(0.85);
        splitter.setEnabled(false);
        this.add(splitter);*/
        container.add(boardPanel, BorderLayout.NORTH);
        container.add(actionPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void startTurn()
    {

    }

}
