package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.MatchInfo;
import it.polimi.ingswPSP35.client.NetworkHandler;
import it.polimi.ingswPSP35.commons.MessageID;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;

public class Login extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private JTextField user;
    private String[] playerinfo = new String[2];
    private NetworkHandler networkHandler;
    private MatchInfo matchInfo;
    private LinkedBlockingQueue<String> input;

    public Login(NetworkHandler networkHandler, MatchInfo matchInfo, LinkedBlockingQueue<String> input) {

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
        user = new JTextField(10);
        namePanel.add(user);
        user.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (user.getText().length() > 0 && user.getText().length() <= 10) {
                    if (e.getKeyCode() == '\n')
                        nextPressed();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        JPanel agePanel = new JPanel();
        agePanel.setLayout(new FlowLayout());
        agePanel.setOpaque(false);
        agePanel.setForeground(Color.BLACK);
        this.add(agePanel, BorderLayout.CENTER);

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

        if (e.getActionCommand().equals("NEXT") && user.getText().length() != 0 && user.getText().length() <= 10) {
            nextPressed();
        } else if (e.getActionCommand().equals("NEXT") && user.getText().length() == 0)
            JOptionPane.showMessageDialog(null, "Insert username, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if (e.getActionCommand().equals("NEXT") && user.getText().length() > 10)
            JOptionPane.showMessageDialog(null, "Username too long, please choose a shorter one!", "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void nextPressed() {
        matchInfo.set(user.getText());
        try {
            input.put(user.getText());
        }
        catch (InterruptedException interruptedException) {
            System.out.println("Error retrieving username");
        }
        this.setVisible(false);
    }
}