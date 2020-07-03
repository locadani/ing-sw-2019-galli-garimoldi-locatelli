package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.NetworkHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.LinkedBlockingQueue;


public class Connection extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private JTextField ipfield;
    private String ip;
    private NetworkHandler networkHandler;
    private LinkedBlockingQueue<String> input;

    public Connection(NetworkHandler networkHandler, LinkedBlockingQueue<String> input) {

        this.input = input;
        this.networkHandler = networkHandler;
        ImageIcon image = new ImageIcon(getClass().getResource("/santorini.png"));
        Image scaledImg = image.getImage().getScaledInstance(640, 640, Image.SCALE_SMOOTH);

        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.setOpaque(false);
        this.add(panel, BorderLayout.SOUTH);

        JPanel infos = new JPanel();
        infos.setLayout(new FlowLayout());
        infos.setOpaque(false);
        panel.add(infos);

        JLabel ip = new JLabel("Insert ip address:");
        ip.setForeground(Color.BLACK);
        ip.setOpaque(true);
        infos.add(ip);
        ipfield = new JTextField(30);
        infos.add(ipfield);
        ipfield.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == '\n')
                        nextPressed();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        JButton next = new JButton("NEXT");
        next.setBackground(Color.GRAY);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        panel.add(next);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
                nextPressed();
       }

    private boolean correctIPAddress(String ip) {
        int value;
        String[] ipParts;

        if(ip.endsWith("."))
            ip = ip.substring(0,ip.length()-2);
        ipParts = ip.split("\\.");

        if (ipParts.length == 4) {
            for (String ipPart : ipParts) {
                try {
                    value = Integer.parseInt(ipPart);
                    if (value < 0 || value > 255)
                        return false;
                }
                catch (Exception e) {
                    return false;
                }
            }
        } else
            return false;
        return true;
    }

    private void nextPressed() {

        ip = ipfield.getText();
        if(ip.length()>0) {
            if (correctIPAddress(ip)) {
                try {
                    input.put(ip);
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                this.setVisible(false);
            } else
                JOptionPane.showMessageDialog(null, "IP format not valid", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null, "Insert IP, please!", "Warning", JOptionPane.WARNING_MESSAGE);

    }
}
