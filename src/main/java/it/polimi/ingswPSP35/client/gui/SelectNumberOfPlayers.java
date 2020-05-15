package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectNumberOfPlayers extends JFrame implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private int numberofplayers = 0;

    public SelectNumberOfPlayers(){

        ImageIcon image = new ImageIcon(getClass().getResource("/santorini.png"));
        Image scaledImg = image.getImage().getScaledInstance(640, 640, Image.SCALE_SMOOTH);

        this.setSize(LARG, ALT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setTitle("Santorini Welcome");

        JLabel backgorund = new JLabel(new ImageIcon(scaledImg));
        backgorund.setLayout(new BorderLayout());
        add(backgorund);

        JPanel centerpanel = new JPanel();
        centerpanel.setOpaque(false);
        centerpanel.setLayout(new FlowLayout());
        backgorund.add(centerpanel, BorderLayout.CENTER);

        JPanel stringpanel = new JPanel();
        stringpanel.setOpaque(false);
        stringpanel.setLayout(new GridLayout(2,1));
        centerpanel.add(stringpanel);

        JLabel string = new JLabel("Choose the number of players for the next game:");
        string.setOpaque(true);
        string.setBackground(Color.white);
        stringpanel.add(string);

        JPanel selectpanel = new JPanel();
        selectpanel.setOpaque(false);
        selectpanel.setLayout(new GridLayout(1, 2));
        centerpanel.add(selectpanel);

        ButtonGroup buttons = new ButtonGroup();

        JRadioButton two = new JRadioButton("2");
        two.setOpaque(false);
        two.addActionListener(this);
        buttons.add(two);
        selectpanel.add(two);

        JRadioButton three = new JRadioButton("3");
        three.setOpaque(false);
        three.addActionListener(this);
        buttons.add(three);
        selectpanel.add(three);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setOpaque(false);
        panel.setForeground(Color.BLACK);
        backgorund.add(panel, BorderLayout.SOUTH);

        JButton next = new JButton("NEXT");
        next.setBackground(Color.GRAY);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        panel.add(next);

        this.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("2"))
            numberofplayers = 2;
        else if(e.getActionCommand().equals("3"))
            numberofplayers = 3;
        else if(e.getActionCommand().equals("NEXT") && numberofplayers ==0)
            JOptionPane.showMessageDialog(null, "Choose the number of players, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && numberofplayers != 0)
            System.exit(0);

    }

    public int getNumberofplayers(){

        return this.numberofplayers;
    }

    public static void main(String[] args){

       SelectNumberOfPlayers test = new SelectNumberOfPlayers();

    }


}
