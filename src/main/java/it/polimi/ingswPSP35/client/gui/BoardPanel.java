package it.polimi.ingswPSP35.client.gui;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel
{

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon(getClass().getResource("/santorini.png"));
        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public BoardPanel(Request request)
    {
        this.setSize(500,300);
        this.setLayout(new GridLayout(5,5));

        for(int i =1; i<26; i++)
        {
            Cell current = new Cell(Integer.toString(i));

            this.add(current);
            current.addActionListener(new CellButtonListener(this, request));

            current.setHorizontalAlignment(SwingConstants.LEFT);
            current.setVerticalAlignment(SwingConstants.NORTH);

        }
    }

    public void updateCell(int cell, int height, String piece, int colour)
    {
        Cell modifyCell = ((Cell) this.getComponent(cell));
        modifyCell.setText(Integer.toString(height));
        modifyCell.update(piece + height);
        switch(colour)
        {
            case 0:
                modifyCell.setBackground(Color.red);
                break;

            case 1:
                modifyCell.setBackground(Color.blue);
                break;

            case 2:
                modifyCell.setBackground(Color.green);
                break;

            default:
                modifyCell.setBackground(Color.CYAN);

        }

    }

}