package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Login extends JFrame implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private JTextField user, insertage;
    private String[] playerinfo = new String[2];

    public Login(){


        ImageIcon image = new ImageIcon(getClass().getResource("/santorini.png"));
        Image scaledImg = image.getImage().getScaledInstance(640, 640, Image.SCALE_SMOOTH);

        this.setSize(LARG, ALT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setTitle("Santorini Welcome");

        /*Container sfondo = getContentPane();
        sfondo.setForeground(Color.BLACK);*/


        JLabel backgorund = new JLabel(new ImageIcon(scaledImg));
        backgorund.setLayout(new BorderLayout());
        add(backgorund);


        JPanel namePanel = new JPanel();
        namePanel.setOpaque(false);
        namePanel.setLayout(new FlowLayout());
        namePanel.setForeground(Color.BLACK);
        backgorund.add(namePanel, BorderLayout.NORTH);

        JLabel username = new JLabel("Insert Username:");
        namePanel.add(username);
        user = new JTextField(15);
        namePanel.add(user);

        JPanel agePanel = new JPanel();
        agePanel.setLayout(new FlowLayout());
        agePanel.setOpaque(false);
        agePanel.setForeground(Color.BLACK);
        backgorund.add(agePanel, BorderLayout.CENTER);

        JLabel age = new JLabel("Now insert your age:");
        agePanel.add(age);
        insertage = new JTextField(2);
        agePanel.add(insertage);

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

        if(e.getActionCommand().equals("NEXT") && user.getText().length() != 0 && insertage.getText().length() != 0){
            playerinfo [0] = user.getText();
            playerinfo [1] = insertage.getText();
            System.exit(0);
        }
        else if(e.getActionCommand().equals("NEXT") && user.getText().length() == 0 && insertage.getText().length() != 0)
            JOptionPane.showMessageDialog(null, "Insert username, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && user.getText().length() != 0 && insertage.getText().length() == 0)
            JOptionPane.showMessageDialog(null, "Insert your age, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && user.getText().length() == 0 && insertage.getText().length() == 0)
            JOptionPane.showMessageDialog(null, "Insert username and age, please!", "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public String[] getPlayerinfo(){

        return this.playerinfo;

    }

    public static void main(String[] args){
        Login test = new Login();


    }



}