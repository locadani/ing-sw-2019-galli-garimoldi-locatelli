package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.NetworkHandler;
import it.polimi.ingswPSP35.commons.MessageID;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SelectDivinities extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private List<String> selectedDivinities = new ArrayList<>();
    private int nPlayers;
    private NetworkHandler networkHandler;


    public SelectDivinities(int nPlayers, NetworkHandler networkHandler, List<String> allDivinities){


        this.networkHandler = networkHandler;
        this.nPlayers = nPlayers;
        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        JPanel divinitiesPanel = new JPanel();
        divinitiesPanel.setOpaque(false);
        divinitiesPanel.setLayout(new GridLayout(3,5));
        this.add(divinitiesPanel, BorderLayout.CENTER);

        JPanel stringpanel = new JPanel();
        stringpanel.setOpaque(false);
        stringpanel.setLayout(new FlowLayout());
        this.add(stringpanel, BorderLayout.NORTH);

        JLabel string = new JLabel("Choose "+nPlayers+" divinities for the next game:");
        stringpanel.add(string);

        for (String divinity : allDivinities) {
            JPanel divinityPanel = new JPanel();
            divinityPanel.setOpaque(false);
            divinityPanel.setLayout(new FlowLayout());
            divinitiesPanel.add(divinityPanel);

            JLabel divinityLabel = new JLabel(new ImageIcon(getClass().getResource("/" + divinity + ".png")));
            divinityPanel.add(divinityLabel);
            JCheckBox divinityCheckBox = new JCheckBox(divinity);
            divinityCheckBox.addActionListener(new CheckBoxListener(divinityCheckBox, selectedDivinities, nPlayers));
            divinityPanel.add(divinityCheckBox);
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
        if(e.getActionCommand().equals("NEXT") && selectedDivinities.size() == nPlayers){

            //Server handles 2 or 3 divinities in the same way
            networkHandler.send(MessageID.CHOOSE2DIVINITIES, selectedDivinities);
            this.setVisible(false);
        }
    }
}
