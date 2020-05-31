package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.*;
import it.polimi.ingswPSP35.client.gui.Daniele.MatchFrame;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Gui implements UInterface {

    private JFrame window = new JFrame();
    private ConfigWindow configWindow;
    private MatchFrame gameWindow;
    private ServerHandler serverHandler;
    private Board board;
    private MatchInfo matchInfo;


    public Gui(ServerHandler serverHandler, Board board) {
        matchInfo = new MatchInfo();
        configWindow = new ConfigWindow(serverHandler, matchInfo);
        gameWindow = new MatchFrame(serverHandler, matchInfo);
        this.serverHandler = serverHandler;
        this.board = board;
        //TODO da fare dopo creazione di ServerHandler
        //  this.gameWindow = new GameWindow();
        // gameWindow.setVisible(false);
    }

    public void getNPlayers() {
        configWindow.setSelectNumberOfPlayersPanel();
    }

    public void getDivinities(int numberOfPlayers) {
        configWindow.setSelectDivinitiesPanel(numberOfPlayers);
    }

    public void getPlayerInfo() {
        configWindow.setLoginPanel();
    }

    public void chooseDivinity(List<String> divinitiesList) {
        configWindow.setChooseDivinitiesPanel(divinitiesList);
    }

    public void getPosition() {
        configWindow.setVisible(false);
        gameWindow.placeWorkers();
    }

    public void startMatch(){gameWindow.startMatch();}

    public void performAction() {
        gameWindow.startTurn();
    }

    public void chooseColour(List<String> availableColors) {
        configWindow.setColorChooserPanel();
    }

    public String getConnectionInfo() {
        LinkedBlockingQueue<String> input = new LinkedBlockingQueue<>();
        String playerInfo ;
        configWindow.setConnectionPanel(input);
        try {
            playerInfo = (String) input.take();
        }
        catch (InterruptedException e){
            playerInfo = "invalid";
        }
        return playerInfo;
    }

    public void update(String[] params) {

        board.update(params);

        for(Integer modifiedCell : board.getModifiedCells()) {
            CellInfo cellInfo = board.getCellInfo(modifiedCell);
            gameWindow.updateCell(modifiedCell, cellInfo.getHeight(), cellInfo.getPiece(), cellInfo.getColour());
        }
        board.clearUpdatedCells();
    }

    public void configUI(ServerHandler serverHandler) {
        //this.configWindow = new ConfigWindow(serverHandler, matchInfo);
        this.serverHandler = serverHandler;
    }

    public void notify(String message){

        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);

    }
}
