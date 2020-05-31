package it.polimi.ingswPSP35.client.gui.Daniele;

import it.polimi.ingswPSP35.client.gui.Cell;
import it.polimi.ingswPSP35.client.gui.CellButtonListener;
import it.polimi.ingswPSP35.client.gui.Request;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.util.Collections;

public class BoardPanel extends JPanel
{

    public BoardPanel(Request request)
    {
        this.setSize(500,300);
        this.setLayout(new GridLayout(5,5));
        for(int i =1; i<26; i++)
        {
            Cell current = new Cell(Integer.toString(i));

            this.add(current);
            current.setOpaque(true);
            current.addActionListener(new CellButtonListener(this, request));

            current.setHorizontalAlignment(SwingConstants.LEFT);
            current.setVerticalAlignment(SwingConstants.NORTH);

        }
    }

    public void updateCell(int cell, int height, String piece, int colour)
    {
        String object = piece;
        if(!piece.equals("E"))
            object = object + height;
        ((Cell) this.getComponent(cell)).update(object);
        switch(colour)
        {
            case 0:
                getComponent(cell).setBackground(ColorUIResource.RED);
                break;
            case 1:
                getComponent(cell).setBackground(ColorUIResource.BLUE);
                break;
            case 2:
                getComponent(cell).setBackground(ColorUIResource.GREEN);
                break;

            default:
                getComponent(cell).setBackground(null);
        }
    }

}