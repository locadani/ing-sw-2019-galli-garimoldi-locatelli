package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.Board;
import it.polimi.ingswPSP35.client.CellInfo;
import it.polimi.ingswPSP35.client.Cli;

import java.util.Scanner;

public class GUI extends Cli {

    private ActionPanel actionPanel;
    private BoardPanel boardPanel;
    private MatchFrame matchFrame;
    private Request request = new Request();
    private Scanner in = new Scanner(System.in);
    private String message;

    public GUI() {

        actionPanel = new ActionPanel(request);
        boardPanel = new BoardPanel(request);
        matchFrame = new MatchFrame(actionPanel, boardPanel);
        matchFrame.setVisible(true);

    }

    @Override
    public String performAction() {
        matchFrame.startTurn();
        return "string";
    }


    @Override
    public void update(Board board)
    {
        //ricevo lista di caselle modificate
        for(Integer modifiedCell : board.getModifiedCells()) {
           /* int r, c, height, colour = -1;
            String piece = null;
            r = Integer.parseInt(params[1]);
            c = Integer.parseInt(params[2]);
            height = Integer.parseInt(params[3]);

            if (params.length > 4) {
                piece = params[4];
                if (piece.equals("WORKER"))
                    colour = Integer.parseInt(params[5]);
            }

            int cell = r * 5 + c;*/
            CellInfo cellInfo = board.getCellInfo(modifiedCell);
            boardPanel.updateCell(modifiedCell, cellInfo.getHeight(), cellInfo.getPiece(), cellInfo.getColour());
        }
    }
}