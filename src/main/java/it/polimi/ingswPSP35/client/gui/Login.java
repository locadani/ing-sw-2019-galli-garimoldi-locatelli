package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.MatchInfo;
import it.polimi.ingswPSP35.client.NetworkHandler;
import it.polimi.ingswPSP35.commons.MessageID;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;

public class Login extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private JTextField user, insertage;
    private String[] playerinfo = new String[2];
    private NetworkHandler networkHandler;
    private MatchInfo matchInfo;
    private LinkedBlockingQueue<String> input;

    public Login(NetworkHandler networkHandler, MatchInfo matchInfo, LinkedBlockingQueue<String> input){

        this.input = input;
        this.matchInfo = matchInfo;
        this.networkHandler = networkHandler;
        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        JPanel namePanel = new JPanel();
        namePanel.setOpaque(false);
        namePanel.setLayout(new FlowLayout());
        namePanel.setForeground(Color.BLACK);
        this.add(namePanel, BorderLayout.NORTH);

        JLabel username = new JLabel("Insert Username:");
        namePanel.add(username);
        user = new JTextField(15);
        namePanel.add(user);

        JPanel agePanel = new JPanel();
        agePanel.setLayout(new FlowLayout());
        agePanel.setOpaque(false);
        agePanel.setForeground(Color.BLACK);
        this.add(agePanel, BorderLayout.CENTER);

        JLabel age = new JLabel("Now insert your age:");
        agePanel.add(age);
        insertage = new JTextField(2);
        agePanel.add(insertage);

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

        if(e.getActionCommand().equals("NEXT") && user.getText().length() != 0 && insertage.getText().length() != 0){
            matchInfo.set(user.getText(),Integer.parseInt(insertage.getText()));
            try {
                input.put(user.getText());
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            this.setVisible(false);
        }
        else if(e.getActionCommand().equals("NEXT") && user.getText().length() == 0 && insertage.getText().length() != 0)
            JOptionPane.showMessageDialog(null, "Insert username, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && user.getText().length() != 0 && insertage.getText().length() == 0)
            JOptionPane.showMessageDialog(null, "Insert your age, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && user.getText().length() == 0 && insertage.getText().length() == 0)
            JOptionPane.showMessageDialog(null, "Insert username and age, please!", "Warning", JOptionPane.WARNING_MESSAGE);
    }

}