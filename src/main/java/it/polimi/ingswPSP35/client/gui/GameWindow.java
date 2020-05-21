package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private static final int LARG = 1331;
    private static final int ALT = 1001;
    private ImageIcon image = new ImageIcon(getClass().getResource("/gamebackgorund.jpg"));
    private ImageIcon board = new ImageIcon(getClass().getResource("/board.png"));
    private Image scaledImg = image.getImage().getScaledInstance(1331, 1001, Image.SCALE_SMOOTH);
    private JLabel background = new JLabel(new ImageIcon(scaledImg));

    public GameWindow(){

        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setUndecorated(true);
        this.setSize(LARG, ALT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Santorini The Game");

        background.setLayout(new BorderLayout());
        this.add(background);

        JPanel northPanel = new JPanel();
        northPanel.setOpaque(false);
        background.add(northPanel, BorderLayout.NORTH);

        JLabel text = new JLabel("GAME ON");
        northPanel.add(text);

        JLayeredPane boardLayer = new JLayeredPane();
        boardLayer.setVisible(true);
        background.add(boardLayer, BorderLayout.CENTER);

        JLabel boardLabel = new JLabel(board);
        boardLabel.setVisible(true);
        boardLayer.add(boardLabel, JLayeredPane.DEFAULT_LAYER);
        boardLabel.setBounds( 0, 0, image.getIconWidth(), image.getIconHeight() );

        JPanel boardPanel = new JPanel();
        boardPanel.setOpaque(false);
        boardLayer.add(boardPanel, JLayeredPane.PALETTE_LAYER);
        boardPanel.setLayout(new GridLayout(5,5));
        boardPanel.setBounds(120,0,796,796);

        JButton one = new JButton("1");
        one.setContentAreaFilled(false);
        one.setBorderPainted(false);
        boardPanel.add(one);

        JButton two = new JButton("2");
        two.setContentAreaFilled(false);
        two.setBorderPainted(false);
        boardPanel.add(two);

        JButton three = new JButton("3");
        three.setContentAreaFilled(false);
        three.setBorderPainted(false);
        boardPanel.add(three);

        JButton four = new JButton("4");
        four.setContentAreaFilled(false);
        four.setBorderPainted(false);
        boardPanel.add(four);

        JButton five = new JButton("5");
        five.setContentAreaFilled(false);
        five.setBorderPainted(false);
        boardPanel.add(five);

        JButton six = new JButton("6");
        six.setContentAreaFilled(false);
        six.setBorderPainted(false);
        boardPanel.add(six);

        JButton seven = new JButton("7");
        seven.setContentAreaFilled(false);
        seven.setBorderPainted(false);
        boardPanel.add(seven);

        JButton eight = new JButton("8");
        eight.setContentAreaFilled(false);
        eight.setBorderPainted(false);
        boardPanel.add(eight);

        JButton nine = new JButton("9");
        nine.setContentAreaFilled(false);
        nine.setBorderPainted(false);
        boardPanel.add(nine);

        JButton ten = new JButton("10");
        ten.setContentAreaFilled(false);
        ten.setBorderPainted(false);
        boardPanel.add(ten);

        JButton eleven = new JButton("11");
        eleven.setContentAreaFilled(false);
        eleven.setBorderPainted(false);
        boardPanel.add(eleven);

        JButton twelve = new JButton("12");
        twelve.setContentAreaFilled(false);
        twelve.setBorderPainted(false);
        boardPanel.add(twelve);

        JButton thirteen = new JButton("13");
        thirteen.setContentAreaFilled(false);
        thirteen.setBorderPainted(false);
        boardPanel.add(thirteen);

        JButton fourteen = new JButton("14");
        fourteen.setContentAreaFilled(false);
        fourteen.setBorderPainted(false);
        boardPanel.add(fourteen);

        JButton fifteen = new JButton("15");
        fifteen.setContentAreaFilled(false);
        fifteen.setBorderPainted(false);
        boardPanel.add(fifteen);

        JButton sixteen = new JButton("16");
        sixteen.setContentAreaFilled(false);
        sixteen.setBorderPainted(false);
        boardPanel.add(sixteen);

        JButton seventeen = new JButton("17");
        seventeen.setContentAreaFilled(false);
        seventeen.setBorderPainted(false);
        boardPanel.add(seventeen);

        JButton eighteen = new JButton("18");
        eighteen.setContentAreaFilled(false);
        eighteen.setBorderPainted(false);
        boardPanel.add(eighteen);

        JButton nineteen = new JButton("19");
        nineteen.setContentAreaFilled(false);
        nineteen.setBorderPainted(false);
        boardPanel.add(nineteen);

        JButton twenty = new JButton("20");
        twenty.setContentAreaFilled(false);
        twenty.setBorderPainted(false);
        boardPanel.add(twenty);

        JButton twentyone = new JButton("21");
        twentyone.setContentAreaFilled(false);
        twentyone.setBorderPainted(false);
        boardPanel.add(twentyone);

        JButton twentytwo = new JButton("22");
        twentytwo.setContentAreaFilled(false);
        twentytwo.setBorderPainted(false);
        boardPanel.add(twentytwo);

        JButton twentythree = new JButton("23");
        twentythree.setContentAreaFilled(false);
        twentythree.setBorderPainted(false);
        boardPanel.add(twentythree);

        JButton twentyfour = new JButton("24");
        twentyfour.setContentAreaFilled(false);
        twentyfour.setBorderPainted(false);
        boardPanel.add(twentyfour);

        JButton twentyfive = new JButton("25");
        twentyfive.setContentAreaFilled(false);
        twentyfive.setBorderPainted(false);
        boardPanel.add(twentyfive);

        JPanel westPanel = new JPanel();
        westPanel.setOpaque(false);
        westPanel.setLayout(new GridLayout(3,1));
        background.add(westPanel, BorderLayout.WEST);

        JLabel workerText = new JLabel("Select the Worker:");
        westPanel.add(workerText);

        JButton male = new JButton("MALE");
        westPanel.add(male);

        JButton woman = new JButton("FEMALE");
        westPanel.add(woman);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setOpaque(false);
        background.add(buttonsPanel, BorderLayout.SOUTH);

        JButton move = new JButton("MOVE");
        buttonsPanel.add(move);

        JButton build = new JButton("BUILD");
        buttonsPanel.add(build);

        JButton godpower = new JButton("GOD POWER");
        buttonsPanel.add(godpower);

        JButton endturn = new JButton("END TURN");
        buttonsPanel.add(endturn);



    }


    public static void main(String[] args){

        GameWindow test = new GameWindow();


    }


}
