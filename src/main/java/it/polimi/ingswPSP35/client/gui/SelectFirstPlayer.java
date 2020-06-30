package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.NetworkHandler;
import it.polimi.ingswPSP35.commons.MessageID;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SelectFirstPlayer extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;

    private NetworkHandler networkHandler;
    private ButtonGroup buttons;
    private List<String> players;

    public SelectFirstPlayer(NetworkHandler networkHandler, List<String> players){

        this.players = players;
        buttons = new ButtonGroup();
        this.networkHandler = networkHandler;
        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        JPanel centerpanel = new JPanel();
        centerpanel.setOpaque(false);
        centerpanel.setLayout(new FlowLayout());
        this.add(centerpanel);

        JPanel stringpanel = new JPanel();
        stringpanel.setOpaque(false);
        stringpanel.setLayout(new GridLayout(2,1));
        centerpanel.add(stringpanel);

        JLabel string = new JLabel("Choose first player:");
        stringpanel.add(string);


        JPanel selectpanel = new JPanel();
        selectpanel.setOpaque(false);
        selectpanel.setLayout(new GridLayout(3, 1));
        centerpanel.add(selectpanel);

        for(String player : players) {
            JRadioButton button = new JRadioButton(player);
            button.setOpaque(false);
            button.setActionCommand(player);
            buttons.add(button);
            selectpanel.add(button);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setOpaque(false);
        panel.setForeground(Color.BLACK);
        this.add(panel, BorderLayout.SOUTH);

        JButton next = new JButton("NEXT");
        next.setBackground(Color.GRAY);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        panel.add(next);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("NEXT") && buttons.getSelection() == null)
            JOptionPane.showMessageDialog(null, "Choose first player, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && buttons.getSelection() != null) {
            networkHandler.send(MessageID.CHOOSEFIRSTPLAYER, players.indexOf(buttons.getSelection().getActionCommand()));
            this.setVisible(false);
        }
    }
}
