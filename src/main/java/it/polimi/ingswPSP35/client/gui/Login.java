package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

//TODO sistemare parti commentate
public class Login extends JPanel implements ActionListener {

    private static final int LARG = 640;
    private static final int ALT = 640;
    private JTextField user, insertage;
    private String[] playerinfo = new String[2];

    public Login(){

        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        /*Container sfondo = getContentPane();
        sfondo.setForeground(Color.BLACK);*/

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

        //this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("NEXT") && user.getText().length() != 0 && insertage.getText().length() != 0){
            playerinfo [0] = user.getText();
            playerinfo [1] = insertage.getText();
            this.setVisible(false);
        }
        else if(e.getActionCommand().equals("NEXT") && user.getText().length() == 0 && insertage.getText().length() != 0)
            JOptionPane.showMessageDialog(null, "Insert username, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && user.getText().length() != 0 && insertage.getText().length() == 0)
            JOptionPane.showMessageDialog(null, "Insert your age, please!", "Warning", JOptionPane.WARNING_MESSAGE);
        else if(e.getActionCommand().equals("NEXT") && user.getText().length() == 0 && insertage.getText().length() == 0)
            JOptionPane.showMessageDialog(null, "Insert username and age, please!", "Warning", JOptionPane.WARNING_MESSAGE);
    }

    /*String[] getInfo(){
        RunnableFuture<String[]> rf = new FutureTask<>(() -> getPlayerInfo());
        SwingUtilities.invokeLater(rf);

        try {

            return rf.get();
        }catch (InterruptedException| ExecutionException ex) {
            ex.printStackTrace();
        }


     return null;
    }*/

    public String[] getPlayerInfo(){

        return this.playerinfo;
    }



    public static void main(String[] args){
        String[] string;

        Login test = new Login();

       //string = test.getInfo();

    }



}