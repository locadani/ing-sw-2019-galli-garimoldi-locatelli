package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.*;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Gui implements UInterface {

    private JFrame window = new JFrame();
    private ConfigWindow configWindow;
    private GameWindow gameWindow;
    private ServerHandler serverHandler;
    private Board board;

    public Gui(ServerHandler serverHandler, Board board) {
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
        gameWindow.disableButtonsPanel();
    }

    @Override
    public void performAction() {

    }

    public void chooseColour(List<String> availableColors) {
        configWindow.setColorChooserPanel();
    }


    public void getConnectionInfo() {
        configWindow.setConnectionPanel();
    }


    public void update(String[] params) {

        for(Integer modifiedCell : board.getModifiedCells()) {
            board.update(params);
            CellInfo cellInfo = board.getCellInfo(modifiedCell);
            gameWindow.updateCell(modifiedCell, cellInfo.getHeight(), cellInfo.getPiece(), cellInfo.getColour());
        }

    }

    @Override
    public void configUI(ServerHandler serverHandler) {

        this.configWindow = new ConfigWindow(serverHandler);
        this.serverHandler = serverHandler;
    }

    public void notify(String message){

        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);

    }
}
