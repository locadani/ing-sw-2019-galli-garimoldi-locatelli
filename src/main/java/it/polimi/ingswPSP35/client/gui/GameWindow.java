package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.server.controller.divinities.Apollo;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private static final int LARG = 1331;
    private static final int ALT = 1001;

    private JPanel buttonsPanel = new JPanel();
    private JButton move = new JButton("MOVE");
    private JButton build = new JButton("BUILD");
    private JButton godpower = new JButton("GOD POWER");
    private JButton endturn = new JButton("END TURN");

    private ImageIcon image = new ImageIcon(getClass().getResource("/gamebackgorund.jpg"));
    private ImageIcon board = new ImageIcon(getClass().getResource("/board.png"));
    private Image scaledImg = image.getImage().getScaledInstance(1331, 1001, Image.SCALE_SMOOTH);
    private JLabel background = new JLabel(new ImageIcon(scaledImg));
    private ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));


    public GameWindow(){
        //String divinity = "apollo";
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setUndecorated(true);
        this.setSize(LARG, ALT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Santorini The Game");
        this.setIconImage(icon.getImage());

        background.setLayout(new BorderLayout());
        this.add(background);

        /*JPanel northPanel = new JPanel();
        northPanel.setOpaque(false);
        background.add(northPanel, BorderLayout.NORTH);

        JLabel text = new JLabel(new ImageIcon(getClass().getResource("/logo.jpeg")));
        northPanel.add(text);*/

        JPanel westPanel = new JPanel();
        westPanel.setOpaque(false);
        westPanel.setLayout(new GridLayout(3,1));
        background.add(westPanel, BorderLayout.WEST);

        JLabel workerText = new JLabel("Select the Worker:");
        westPanel.add(workerText);

        JButton male = new JButton("MALE");
        westPanel.add(male);

        JButton female = new JButton("FEMALE");
        westPanel.add(female);


        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setOpaque(false);
        background.add(buttonsPanel, BorderLayout.SOUTH);

        buttonsPanel.add(move);

        buttonsPanel.add(build);

        buttonsPanel.add(godpower);

        buttonsPanel.add(endturn);


        JLayeredPane boardLayer = new JLayeredPane();
        boardLayer.setVisible(true);
        boardLayer.setPreferredSize(new Dimension(797,797));
        background.add(boardLayer, BorderLayout.CENTER);

        JLabel boardLabel = new JLabel(board);
        //boardLabel.setSize(boardLayer.getPreferredSize());
        //boardLabel.setLocation(40,100);
        boardLayer.add(boardLabel, JLayeredPane.DEFAULT_LAYER);
        boardLabel.setBounds( 40, 100, image.getIconWidth(), image.getIconHeight() );

        JPanel boardPanel = new JPanel();
        boardPanel.setOpaque(false);
        boardPanel.setVisible(true);
        //boardPanel.setSize(boardLayer.getPreferredSize());
        //boardPanel.setLocation(40,100);
        boardLayer.add(boardPanel, JLayeredPane.PALETTE_LAYER);
        boardPanel.setLayout(new GridLayout(5,5));
        boardPanel.setBounds(160,80,797,797);

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

        /*JPanel eastPanel = new JPanel();
        eastPanel.setOpaque(false);
        eastPanel.setVisible(true);
        background.add(eastPanel, BorderLayout.EAST);

        JLabel apollo = new JLabel(new ImageIcon(getClass().getResource("/001.png")));
        if(divinity == "apollo")
            apollo.setVisible(true);
        else apollo.setVisible(false);
        eastPanel.add(apollo);

        JLabel artemis = new JLabel(new ImageIcon(getClass().getResource("/002.png")));
        if(divinity == "artemis")
            artemis.setVisible(true);
        else artemis.setVisible(false);
        eastPanel.add(artemis);*/




    }

    public void disableButtonsPanel(){
        
        move.setEnabled(false);
        build.setEnabled(false);
        godpower.setEnabled(false);
        endturn.setEnabled(false);

    }


    public static void main(String[] args){

        GameWindow test = new GameWindow();
        test.disableButtonsPanel();


    }


}
