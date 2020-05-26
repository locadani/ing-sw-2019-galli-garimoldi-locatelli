package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorChooser extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private String color;
    private String colorSelected;

    public ColorChooser(){

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

        ButtonGroup buttons = new ButtonGroup();

        JRadioButton red = new JRadioButton("RED");
        red.setOpaque(false);
        red.setForeground(Color.RED);
        red.addActionListener(this);
        buttons.add(red);
        selectpanel.add(red);

        JRadioButton green = new JRadioButton("GREEN");
        green.setOpaque(false);
        green.setForeground(Color.GREEN);
        green.addActionListener(this);
        buttons.add(green);
        selectpanel.add(green);

        JRadioButton blue = new JRadioButton("BLUE");
        blue.setOpaque(false);
        blue.setForeground(Color.BLUE);
        blue.addActionListener(this);
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

        //this.setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("RED"))
            colorSelected = "RED";
        else if(e.getActionCommand().equals("GREEN"))
            colorSelected = "GREEN";
        else if(e.getActionCommand().equals("BLUE"))
            colorSelected = "BLUE";
        else if(e.getActionCommand().equals("NEXT") && colorSelected == null)
            JOptionPane.showMessageDialog(null, "Choose a color, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && colorSelected != null) {
            color = colorSelected;
            this.setVisible(false);
        }
    }



    public String getColor(){

        return this.color;
    }
}
