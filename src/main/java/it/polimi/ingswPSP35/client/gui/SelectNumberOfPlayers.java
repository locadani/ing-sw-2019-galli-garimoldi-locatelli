package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.NetworkHandler;
import it.polimi.ingswPSP35.commons.MessageID;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectNumberOfPlayers extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private ButtonGroup buttons;
    private NetworkHandler networkHandler;

    public SelectNumberOfPlayers(NetworkHandler networkHandler){

        this.networkHandler = networkHandler;
        buttons = new ButtonGroup();
        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        JPanel centerpanel = new JPanel();
        centerpanel.setOpaque(false);
        centerpanel.setLayout(new FlowLayout());
        this.add(centerpanel, BorderLayout.CENTER);

        JLabel string = new JLabel("Choose the number of players for the next game:");
        string.setOpaque(true);
        string.setBackground(Color.white);
        centerpanel.add(string);

        JRadioButton two = new JRadioButton("2");
        two.setOpaque(false);
        two.setActionCommand("2");
        buttons.add(two);
        centerpanel.add(two);

        JRadioButton three = new JRadioButton("3");
        three.setOpaque(false);
        three.setActionCommand("3");
        buttons.add(three);
        centerpanel.add(three);

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
            JOptionPane.showMessageDialog(null, "Choose the number of players, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && buttons.getSelection() != null) {
            networkHandler.send(MessageID.GETNUMBEROFPLAYERS, buttons.getSelection().getActionCommand());
            this.setVisible(false);
        }
    }
}
