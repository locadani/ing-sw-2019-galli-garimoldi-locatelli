package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MessagesHandler implements Runnable {

    Gson gson = new Gson();
    ClientConnection clientConnection = null;
    Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;
    UInterface uInterface;
    String[][] board;

    public MessagesHandler(String[][] board, int UI) {
        if (UI == 0)
            uInterface = new TestFile();
        this.board = board;
    }

    @Override
    public void run() {

        String toSendMessage ;
        String[] params;
        boolean completed;
        boolean canContinue;
        boolean completedSetup;
        int nPlayers;

        do {
            //chidere ip e porta
            completed = tryConnectionSetup("127.0.0.1", 7777);
        } while (!completed);

        String[] playerInfo;
        List<String> colours = null;
        completedSetup = false;
        while (!completedSetup) {
            try {
                System.out.println("Waiting");

                completed = false;
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
                        for(int i=1;i<playerInfo.length;i++)
                        {
                            toSendMessage = toSendMessage + "|" + playerInfo[i];
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
                        while (!completed) {
                            clientConnection.send(toSendMessage);
                            completed = true;
                        }
                        break;

                    case "GETDIVINITY":
                        divinitiesList = new ArrayList<String>(Arrays.asList(params));
                        divinitiesList.remove(0);

                        toSendMessage = uInterface.chooseDivinity(divinitiesList);
                        while (!completed) {
                            clientConnection.send(toSendMessage);
                            completed = true;
                        }
                        break;

                    case "UPDATE":
                        updateBoard(params);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exc");
                completed = false;
            }

        }

        //inizio partita
        canContinue = true;
        boolean repeat = false;
        while (canContinue) {

            try {
                params = receiveFromServer();

                switch (params[0]) {
                    case "NOTIFICATION":
                        System.out.println(params[1]);
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
            } catch (Exception e) {
                canContinue = false;
            }

        }
    }

    private boolean tryConnectionSetup(String ip, int port) {
        boolean completed = false;
        try {
            socket = new Socket(ip, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            clientConnection = new ClientConnection(input, output, socket);
            completed = true;
        } catch (IOException e) {
            completed = false;
        }
        return completed;
    }

    private void modifyBoard(int r, int c, int height, String piece) {
        board[r][c] = getCode(piece, height);
    }

    private void modifyBoard(int r, int c, int height, String piece, int colour) {
        board[r][c] = getCode(piece, height, colour);
        //dare colore
    }

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

    private void waitForResponse() throws IOException, ClassNotFoundException {
        String params[];
        params = receiveFromServer();

        switch (params[0]) {
            case "UPDATE":
                updateBoard(params);
                break;

            case "NOTIFICATION":
                System.out.println(params[1]);
                break;
        }
    }

    private String[] receiveFromServer() throws IOException, ClassNotFoundException {
        String receivedMessage = null;
        String[] serverInfo = new String[1];

        receivedMessage = clientConnection.receive();
        if (receivedMessage.contains("|"))
            serverInfo = receivedMessage.split("\\|");
        else
            serverInfo[0] = receivedMessage;
        return serverInfo;
    }



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