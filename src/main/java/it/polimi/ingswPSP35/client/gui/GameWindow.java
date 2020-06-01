package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.MatchInfo;
import it.polimi.ingswPSP35.client.NetworkHandler;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.commons.RequestedAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private static final int LARG = 1331;
    private static final int ALT = 1001;

    private Request request;

    private BoardPanel buttonsPanel;
    private JButton move = new JButton("MOVE");
    private JButton build = new JButton("BUILD");
    private JButton godPower = new JButton("GODPOWER");
    private JButton endTurn = new JButton("END TURN");
    private JButton next = new JButton("NEXT");

    private ImageIcon image = new ImageIcon(getClass().getResource("/gamebackgorund.jpg"));
    private ImageIcon board = new ImageIcon(getClass().getResource("/board.png"));
    private Image scaledImg = image.getImage().getScaledInstance(LARG, ALT, Image.SCALE_SMOOTH);
    private JLabel background = new JLabel(new ImageIcon(scaledImg));
    private ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));

    private NetworkHandler networkHandler;
    private MatchInfo matchInfo;

    public GameWindow(NetworkHandler networkHandler, MatchInfo matchInfo) {

        this.matchInfo = matchInfo;
        this.networkHandler = networkHandler;
        request = new Request();
        buttonsPanel = new BoardPanel(request);
        this.setSize(LARG, ALT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Santorini The Game");
        this.setIconImage(icon.getImage());

        background.setLayout(new BorderLayout());
        this.add(background);

        /*JPanel northPanel = new JPanel();
        northPanel.setOpaque(false);
        background.add(northPanel, BorderLayout.NORTH);

        JLabel text = new JLabel(new ImageIcon(getClass().getResource("/logo.jpeg")));
        northPanel.add(text);*/

        JPanel westPanel = new JPanel();
        westPanel.setOpaque(false);
        westPanel.setLayout(new GridLayout(3, 1));
        background.add(westPanel, BorderLayout.WEST);

        JLabel workerText = new JLabel("Select the Worker:");
        westPanel.add(workerText);

        JButton male = new JButton("MALE");
        westPanel.add(male);

        JButton female = new JButton("FEMALE");
        westPanel.add(female);


        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setOpaque(false);
        background.add(buttonsPanel, BorderLayout.SOUTH);

        buttonsPanel.add(move);
        buttonsPanel.add(build);
        buttonsPanel.add(godPower);
        buttonsPanel.add(endTurn);
        buttonsPanel.add(next);

        endTurn.setActionCommand("ENDTURN");
        move.addActionListener(new ActionButtonListener(move, request));
        build.addActionListener(new ActionButtonListener(build, request));
        godPower.addActionListener(new ActionButtonListener(godPower, request));
        endTurn.addActionListener(new ActionButtonListener(endTurn, request));

        JLayeredPane boardLayer = new JLayeredPane();
        boardLayer.setVisible(true);
        boardLayer.setPreferredSize(new Dimension(797, 797));
        background.add(boardLayer, BorderLayout.CENTER);

        JLabel boardLabel = new JLabel(board);
        boardLayer.add(boardLabel, JLayeredPane.DEFAULT_LAYER);
        boardLabel.setBounds(40, 100, image.getIconWidth(), image.getIconHeight());

        JPanel boardPanel = new JPanel();
        boardPanel.setOpaque(false);
        boardPanel.setVisible(true);
        boardLayer.add(boardPanel, JLayeredPane.PALETTE_LAYER);
        boardPanel.setLayout(new GridLayout(5, 5));
        boardPanel.setBounds(160, 80, 797, 797);
    }

    public void placeWorkers() {
        disableButtonsPanel();
        setVisible(true);
        removeActionListeners(next);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(request.getWorker() != 0) {
                    networkHandler.send(MessageID.PLACEWORKER, new Coordinates(request.getWorker()));
                }
            }
        });
        buttonsPanel.setVisible(true);
    }

    public void startMatch()
    {
        removeActionListeners(next);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(request.isReady())
                {
                    RequestedAction requestedAction = request.getRequestedAction();
                    networkHandler.send(MessageID.PERFORMACTION, requestedAction);
                }
            }
        });
    }

    public void startTurn()
    {
        request.reset();
       // JOptionPane.showMessageDialog(this, "Your turn started");
    }

    public void disableButtonsPanel() {

        move.setEnabled(false);
        build.setEnabled(false);
        godPower.setEnabled(false);
        endTurn.setEnabled(false);

    }

    public void enableButtonsPanel() {

        move.setEnabled(true);
        build.setEnabled(true);
        godPower.setEnabled(true);
        endTurn.setEnabled(true);

    }

    public void updateCell(int cell, int height, String piece, int colour) {
        buttonsPanel.updateCell(cell, height, piece, colour);
    }

    private void removeActionListeners(JButton button)
    {
        for(ActionListener listener : button.getActionListeners())
        {
            button.removeActionListener(listener);
        }
    }
}
