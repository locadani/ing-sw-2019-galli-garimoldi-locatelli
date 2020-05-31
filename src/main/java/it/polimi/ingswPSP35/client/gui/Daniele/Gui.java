/*package it.polimi.ingswPSP35.client.gui.Daniele;

import it.polimi.ingswPSP35.client.gui.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gui {

    private static String board[][] = new String[5][5];
    private static ActionPanel actionPanel;
    private static BoardPanel boardPanel;
    private static MatchFrame matchFrame;
    private static Request request = new Request();
    private static Scanner in = new Scanner(System.in);
    private static String message;

    public static void main(String[] args) {
        actionPanel = new ActionPanel(request);
        boardPanel = new BoardPanel(request);
        matchFrame = new MatchFrame(actionPanel, boardPanel);
        matchFrame.setVisible(true);
        while (true) {
            message = in.nextLine();
            update(message);
        }

    }

    public static void update(String message) {
        int r, c, height, colour;
        String[] params = message.split(":");
        String piece = null;
        r = Integer.parseInt(params[1]);
        r--;
        c = Integer.parseInt(params[2]);
        c--;
        height = Integer.parseInt(params[3]);

        if (params.length > 4) {
            piece = params[4];
        }

        int cell = r * 5 + c;
        boardPanel.updateCell(cell, height, piece);

    }
}*/