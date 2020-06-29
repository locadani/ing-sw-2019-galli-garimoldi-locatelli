package it.polimi.ingswPSP35.client.gui;

import it.polimi.ingswPSP35.client.*;
import it.polimi.ingswPSP35.commons.ReducedSquare;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

public class Gui implements UInterface {

    private ConfigWindow configWindow;
    private GameWindow gameWindow;
    private NetworkHandler networkHandler;
    private ReducedBoard reducedBoard;
    private MatchInfo matchInfo;


    public Gui(NetworkHandler networkHandler) {
        matchInfo = new MatchInfo();
        configWindow = new ConfigWindow(networkHandler, matchInfo);
        reducedBoard = new ReducedBoard();
        this.networkHandler = networkHandler;
        //TODO da fare dopo creazione di NetworkHandler
        //  this.gameWindow = new GameWindow();
        // gameWindow.setVisible(false);
    }

    public void getNPlayers() {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                configWindow.setSelectNumberOfPlayersPanel();
                return null;
            }
        };
        swingWorker.execute();
    }

    public void choose2Divinities(List<String> allDivinities) {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground()  {
                getDivinities(2, allDivinities);
                return null;
            }
        };
        swingWorker.execute();


    }

    public void choose3Divinities(List<String> allDivinities) {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground()  {
                getDivinities(3, allDivinities);
                return null;
            }
        };
        swingWorker.execute();
    }

    private void getDivinities(int numberOfPlayers, List<String> allDivinities) {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground()  {
                configWindow.setSelectDivinitiesPanel(numberOfPlayers, allDivinities);
                return null;
            }
        };
        swingWorker.execute();
    }

    public String getPlayerInfo() {
        LinkedBlockingQueue<String> input = new LinkedBlockingQueue<>();
        SwingWorker<String, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws InterruptedException {
                configWindow.setLoginPanel(input);
                String playerInfo ;
                playerInfo = (String) input.take();
                return playerInfo;
            }
        };
        swingWorker.execute();
        String returnValue;
        try {
            returnValue = swingWorker.get();
        }
        catch (InterruptedException|ExecutionException e) {
            returnValue = "invalid";
        }
        return returnValue;
    }

    public void pickDivinity(List<String> divinitiesList) {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                configWindow.setChooseDivinitiesPanel(divinitiesList);
                return null;
            }
        };
        swingWorker.execute();
    }

    public void placeWorker() {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                gameWindow.setColorPanel();
                configWindow.setVisible(false);
                gameWindow.placeWorkers();
                return null;
            }
        };
        swingWorker.execute();
    }

    public void setMatchInfo(Map<String, String> userToDivinity) {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                matchInfo.set(userToDivinity);
                gameWindow = new GameWindow(networkHandler, matchInfo);
                return null;
            }
        };
        swingWorker.execute();

    }

    public void startMatch(){
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                gameWindow.startMatch();
                return null;
            }
        };
        swingWorker.execute();
    }

    public void performAction() {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                gameWindow.startTurn();
                gameWindow.enableButtonsPanel();
                return null;
            }
        };
        swingWorker.execute();
    }

    public void chooseColour(List<String> availableColors) {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                configWindow.setColorChooserPanel(availableColors);
                return null;
            }
        };
        swingWorker.execute();
    }

    public String getConnectionInfo() {
        LinkedBlockingQueue<String> input = new LinkedBlockingQueue<>();
        String connectionInfo = "127.0.0.1";
        SwingWorker<String, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws InterruptedException {
                configWindow.setConnectionPanel(input);
                String playerInfo ;
                playerInfo = (String) input.take();
                return playerInfo;
            }
        };
        swingWorker.execute();

        try {
            connectionInfo = swingWorker.get();
        }
        catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
        }

        return connectionInfo;
    }

    public void updateBoard(List<ReducedSquare> changedSquares) {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
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
                    } else if (square.getHeight() != 0) {
                        piece = "B";
                    } else piece = "E";
                    gameWindow.updateCell(square.getCoordinates().getInt(), square.getHeight(), piece, colour);
                }
                return null;
            }
        };
        swingWorker.execute();

    }

    public void displayNotification(String message){
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                JOptionPane.showMessageDialog(null, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
        };
        swingWorker.execute();
    }
}
