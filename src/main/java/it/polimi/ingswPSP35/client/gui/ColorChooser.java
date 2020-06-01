package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.MatchInfo;
import it.polimi.ingswPSP35.client.NetworkHandler;
import it.polimi.ingswPSP35.commons.MessageID;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorChooser extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private String color;
    private String colorSelected;
    private NetworkHandler networkHandler;
    private ButtonGroup buttons;
    private MatchInfo matchInfo;

    public ColorChooser(NetworkHandler networkHandler, MatchInfo matchInfo){

        this.matchInfo = matchInfo;
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

        JLabel string = new JLabel("Choose a color:");
        stringpanel.add(string);


        JPanel selectpanel = new JPanel();
        selectpanel.setOpaque(false);
        selectpanel.setLayout(new GridLayout(3, 1));
        centerpanel.add(selectpanel);


        JRadioButton red = new JRadioButton("RED");
        red.setOpaque(false);
        red.setForeground(Color.RED);
        red.setActionCommand("RED");
        buttons.add(red);
        selectpanel.add(red);

        JRadioButton green = new JRadioButton("GREEN");
        green.setOpaque(false);
        green.setForeground(Color.GREEN);
        green.setActionCommand("GREEN");
        buttons.add(green);
        selectpanel.add(green);

        JRadioButton blue = new JRadioButton("BLUE");
        blue.setOpaque(false);
        blue.setForeground(Color.BLUE);
        blue.setActionCommand("BLUE");
        buttons.add(blue);
        selectpanel.add(blue);


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
            JOptionPane.showMessageDialog(null, "Choose a color, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && buttons.getSelection() != null) {
            matchInfo.setColour(buttons.getSelection().getActionCommand());
            networkHandler.send(MessageID.CHOOSECOLOUR, buttons.getSelection().getActionCommand());
            this.setVisible(false);
        }
    }



    public String getColor(){

        return this.color;
    }
}
