package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.*;
import it.polimi.ingswPSP35.commons.ReducedSquare;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Gui implements UInterface {

    private JFrame window = new JFrame();
    private ConfigWindow configWindow;
    private GameWindow gameWindow;
    private NetworkHandler networkHandler;
    private ReducedBoard reducedBoard;

    public Gui(NetworkHandler networkHandler, ReducedBoard reducedBoard) {
        this.networkHandler = networkHandler;
        this.reducedBoard = reducedBoard;
        //TODO da fare dopo creazione di ServerHandler
        //  this.gameWindow = new GameWindow();
        // gameWindow.setVisible(false);
    }

    public void getNPlayers() {
        configWindow.setSelectNumberOfPlayersPanel();
    }

    public void choose2Divinities(List<String> allDivinities) {
        getDivinities(2, allDivinities);
    }

    public void choose3Divinities(List<String> allDivinities) {
        getDivinities(3, allDivinities);
    }

    public void getDivinities(int numberOfPlayers, List<String> allDivinities) {
        configWindow.setSelectDivinitiesPanel(numberOfPlayers);
    }

    //TODO get playerinfo implementation
    public void getPlayerInfo() {
        configWindow.setLoginPanel();
    }


    public void pickDivinity(List<String> divinitiesList) {
        configWindow.setChooseDivinitiesPanel(divinitiesList);
    }

    public void placeWorker() {
        gameWindow.disableButtonsPanel();
    }

    @Override
    public void performAction() {

    }

    public void chooseColour(List<String> availableColors) {
        configWindow.setColorChooserPanel();
    }


    public String getConnectionInfo() {
        LinkedBlockingQueue<String> input = new LinkedBlockingQueue<String>();
        configWindow.setConnectionPanel();
        try {
            return (String) input.take();
        }
        catch (InterruptedException e){}
        return null;
    }


    public void updateBoard(List<ReducedSquare> changedSquares) {
        reducedBoard.update(changedSquares);
        for (ReducedSquare square : changedSquares) {
            //TODO placeholder logic
            String piece;
            int colour = -1;
            if (square.HasDome())
                piece = "D";
            else if (square.getWorker() != null) {
                piece = "W";
                colour = square.getWorker().getColour();
            }
            else if (square.getHeight() != 0) {
                piece = "B";
            }
            else piece = "E";
            gameWindow.updateCell(square.getCoordinates().getInt(), square.getHeight(), piece, colour);
        }
    }

    public void displayNotification(String message){

        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);

    }
}
