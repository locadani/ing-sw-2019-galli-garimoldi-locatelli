package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.server.Exceptions.DisconnectedException; //TODO va bene?
import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserAction implements Runnable {


    private Gson gson = new Gson();
    private ClientConnection clientConnection = null;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private UInterface uInterface;
    private String[][] board;
    private ServerPinger pinger;
    private Thread pingerThread;

    public UserAction(String[][] board, int UI) {
        if (UI == 0)
            uInterface = new TestFile();
        this.board = board;
    }

    @Override
    public void run() {

        String toSendMessage;
        String[] params;
        boolean completed;
        boolean canContinue;
        boolean completedSetup;
        int nPlayers;

        do {
            //chidere ip e porta
            completed = connectionSetup("127.0.0.1", 7777);
        } while (!completed);


        try {
            String[] playerInfo;
            List<String> colours = null;
            completedSetup = false;
            while (!completedSetup) {
                System.out.println("Waiting");
                params = receiveFromServer();

                switch (params[0]) {

                    case "NOTIFICATION":
                        if (params[1].equals("COMPLETEDSETUP"))
                            completedSetup = true;
                        else
                            System.out.println(params[1]);
                        break;

                    case "NPLAYERS":
                        nPlayers = uInterface.getNPlayers();
                        clientConnection.send(Integer.toString(nPlayers));
                        break;

                    case "PLAYERINFO":
                        playerInfo = uInterface.getPlayerInfo();

                        toSendMessage = playerInfo[0];
                        for (int i = 1; i < playerInfo.length; i++) {
                            toSendMessage = toSendMessage + ":" + playerInfo[i];
                        }
                        clientConnection.send(toSendMessage);
                        break;

                    case "CHOOSECOLOUR":
                        colours = new ArrayList<String>(Arrays.asList(params));
                        colours.remove(0);
                        toSendMessage = uInterface.chooseColour(colours);
                        clientConnection.send(toSendMessage);
                        break;

                    case "PLACEWORKER":
                        toSendMessage = String.valueOf(uInterface.getPosition());
                        clientConnection.send(toSendMessage);
                        break;

                    case "GETNDIVINITIES":
                        List<String> divinitiesList = uInterface.getDivinities(Integer.parseInt(params[1]));
                        toSendMessage = gson.toJson(divinitiesList);
                        clientConnection.send(toSendMessage);
                        break;

                    case "GETDIVINITY":
                        divinitiesList = new ArrayList<String>(Arrays.asList(params));
                        divinitiesList.remove(0);

                        toSendMessage = uInterface.chooseDivinity(divinitiesList);
                        clientConnection.send(toSendMessage);
                        break;

                    case "UPDATE":
                        updateBoard(params);
                }

                pingerThread.interrupt();
            }

            //inizio partita
            canContinue = true;
            boolean repeat = false;
            while (canContinue) {

                params = receiveFromServer();


                switch (params[0]) {
                    case "NOTIFICATION":
                        System.out.println(params[1]); //TODO chiama interfaccia
                        break;

                    case "PERFORMACTION":
                        toSendMessage = uInterface.performAction();
                        clientConnection.send(toSendMessage);
                        break;

                    case "FINISHEDMATCH":
                        canContinue = false;
                        //perche finisce
                        break;

                    case "UPDATE":
                        updateBoard(params);

                }
                pingerThread.interrupt();
            }

        }
        catch (IOException e) {
            System.out.println("Somebody disconnected");
        }
        pingerThread.interrupt();
    }

    /**
     * Creates connection with server
     * @param ip   Server IP Address
     * @param port Server port
     * @return true if connected, false otherwise
     */
    private boolean connectionSetup(String ip, int port) {
        boolean completed;
        try {
            socket = new Socket(ip, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            clientConnection = new ClientConnection(input, output, socket);
            completed = true;
        }
        catch (IOException e) {
            completed = false;
        }
        return completed;
    }

    /**
     * Applies changes to board
     * @param r      row to modify
     * @param c      column to modify
     * @param height height of the piece
     * @param piece  piece to place
     */
    private void modifyBoard(int r, int c, int height, String piece) {
        board[r][c] = getCode(piece, height);
    }

    /**
     * Applies changes to board
     * @param r      row to modify
     * @param c      column to modify
     * @param height height of the piece
     * @param piece  piece to place
     * @param colour colour that represents player
     */
    private void modifyBoard(int r, int c, int height, String piece, int colour) {
        board[r][c] = getCode(piece, height, colour);
        //dare colore
    }

    /**
     * Get code to place on board that identifies the piece
     * @param piece  piece to be represented
     * @param height height of the piece
     * @param colour colour that represents player
     * @return code associated to piece
     */
    private String getCode(String piece, int height, int colour) {
        String result = "";
        result = getCode(piece, height);
        switch (colour) {
            case 0:
                result = AnsiCode.RED + result + AnsiCode.RESET;
                break;
            case 1:
                result = AnsiCode.GREEN + result + AnsiCode.RESET;
                break;
            case 2:
                result = AnsiCode.BLUE + result + AnsiCode.RESET;
                break;
        }
        result = result + height;
        return result;
    }

    /**
     * Get code to place on board that identifies the piece
     * @param piece  piece to be represented
     * @param height height of the piece
     * @return code associated to piece
     */
    private String getCode(String piece, int height) {
        String result = "";
        switch (piece) {
            case "DOME":
                result = "D" + height;
                break;

            case "WORKER":
                result = "W";
                break;

            case "BLOCK":
                result = "B" + height;
                break;

            case "":
                result = "E";

        }
        return result;
    }

    /**
     * Waits for server request
     * @return parameters received from server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private String[] receiveFromServer() throws DisconnectedException {
        String receivedMessage = null;
        String[] serverInfo = new String[1];

        //int pings = 0;
        do {
            try {
                receivedMessage = clientConnection.receive();

                // pings++;
            }
            catch (IOException e) {
                throw new DisconnectedException("Client threw disconnEcc");
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (receivedMessage.equals("PING"));

        pingerThread = new Thread(new ServerPinger(output));
        pingerThread.start();

        if (receivedMessage.contains(":"))
            serverInfo = receivedMessage.split(":");
        else
            serverInfo[0] = receivedMessage;
        return serverInfo;

    }

    /**
     * Modifies specific cell of board
     * @param params Row, column, height and colour of the piece to insert
     */
    private void updateBoard(String[] params) {
        int r, c, height, colour;
        String piece;
        r = Integer.parseInt(params[1]);
        c = Integer.parseInt(params[2]);
        height = Integer.parseInt(params[3]);
        if (params.length > 4) {
            piece = params[4];
            if (piece.equals("WORKER")) {
                colour = Integer.parseInt(params[5]);
                modifyBoard(r, c, height, piece, colour);
            } else
                modifyBoard(r, c, height, piece);

        } else
            modifyBoard(r, c, height, "");
        Printer.printboard(board);
    }
}