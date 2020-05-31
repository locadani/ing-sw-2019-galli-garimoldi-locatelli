package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Connection extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private JTextField ipfield, portfield;
    private String ip, port;
    private ServerHandler serverHandler;

    public Connection(ServerHandler serverHandler) {

        this.serverHandler = serverHandler;
        ImageIcon image = new ImageIcon(getClass().getResource("/santorini.png"));
        Image scaledImg = image.getImage().getScaledInstance(640, 640, Image.SCALE_SMOOTH);

        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.setOpaque(false);
        this.add(panel, BorderLayout.SOUTH);

        JPanel infos = new JPanel();
        infos.setLayout(new FlowLayout());
        infos.setOpaque(false);
        panel.add(infos);

        JLabel ip = new JLabel("Insert ip address:");
        ip.setForeground(Color.WHITE);
        infos.add(ip);
        ipfield = new JTextField(30);
        infos.add(ipfield);
        JLabel port = new JLabel("Insert port number:");
        port.setForeground(Color.RED);
        infos.add(port);
        portfield = new JTextField(4);
        infos.add(portfield);


        JButton next = new JButton("NEXT");
        next.setBackground(Color.GRAY);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        panel.add(next);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("NEXT") && ipfield.getText().length() != 0 && portfield.getText().length() != 0){

            ip = ipfield.getText();
            port = portfield.getText();
            serverHandler.initializeConnection(ip, Integer.parseInt(port));
            this.setVisible(false);
        }
        else if(e.getActionCommand().equals("NEXT") && ipfield.getText().length() == 0 && portfield.getText().length() != 0)
            JOptionPane.showMessageDialog(null, "Insert ip, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && ipfield.getText().length() != 0 && portfield.getText().length() == 0)
            JOptionPane.showMessageDialog(null, "Insert port number, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && ipfield.getText().length() == 0 && portfield.getText().length() == 0)
            JOptionPane.showMessageDialog(null, "Insert ip and port number, please!", "Warning", JOptionPane.WARNING_MESSAGE);
    }

}
