package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private static final int LARG = 797;
    private static final int ALT = 797;
    private JPanel boardPanel = new JPanel();

    private ImageIcon board = new ImageIcon(getClass().getResource("/board.png"));

    public BoardPanel(Request request){

        this.setSize(LARG, ALT);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        JLayeredPane boardLayer = new JLayeredPane();
        boardLayer.setVisible(true);
        boardLayer.setPreferredSize(new Dimension(797,797));
        this.add(boardLayer, BorderLayout.CENTER);

        JLabel boardLabel = new JLabel(board);
        //boardLabel.setSize(boardLayer.getPreferredSize());
        //boardLabel.setLocation(40,100);
        boardLayer.add(boardLabel, JLayeredPane.DEFAULT_LAYER);
        boardLabel.setBounds( 150, 20, board.getIconWidth(), board.getIconHeight() );

        boardPanel.setOpaque(false);
        boardPanel.setVisible(true);
        //boardPanel.setSize(boardLayer.getPreferredSize());
        //boardPanel.setLocation(40,100);
        boardLayer.add(boardPanel, JLayeredPane.PALETTE_LAYER);
        boardPanel.setLayout(new GridLayout(5,5));
        boardPanel.setBounds(150,20,797,797);

        for(int i =1; i<26; i++)
        {
            Cell current = new Cell(Integer.toString(i));

            boardPanel.add(current);
            current.addActionListener(new CellButtonListener(this, request));

            current.setHorizontalAlignment(SwingConstants.LEFT);
            current.setVerticalAlignment(SwingConstants.NORTH);
            current.setOpaque(true);
        }
    }

    public void updateCell(int cell, int height, String piece, int colour)
    {
        Cell modifyCell = ((Cell) boardPanel.getComponent(cell));
        String object = piece;
        if(!piece.equals("E"))
            object = object+height;
        modifyCell.update(object);
        switch(colour)
        {
            case 0:
                modifyCell.setBackground(Color.RED);
                break;

            case 1:
                modifyCell.setBackground(Color.GREEN);
                break;

            case 2:
                modifyCell.setBackground(Color.BLUE);
                break;

            default:
                modifyCell.setBackground(null);

        }
    }
}