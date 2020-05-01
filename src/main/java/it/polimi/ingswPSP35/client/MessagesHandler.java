package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
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
        this.board = board;
        if (UI == 0)
            uInterface = new Cli();
    }

    @Override
    public void run() {

        String receivedMessage;
        String toSendMessage = null;
        String[] params = new String[5];
        int workersPlaced = 0;
        boolean completed = false;
        boolean canContinue = true;
        boolean completedSetup = false;
        int nPlayers;
        //connection
        String ip = "127.0.0.1";
        while (!completed) {
            try {
                socket = new Socket(ip, 7777);
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                clientConnection = new ClientConnection(input, output, socket);
                completed = true;
            } catch (IOException e) {
                completed = false;
            }
        }

        String[] playerInfo;
        while (!completedSetup) {
            try {
                System.out.println("Waiting");
                receivedMessage = clientConnection.receive();
                System.out.println(receivedMessage);
                if (receivedMessage.contains("|"))
                    params = receivedMessage.split("\\|");
                else
                    params[0] = receivedMessage;
                completed = false;

                switch (params[0]) {

                    case "NOTIFICATION":
                        if (params[1].equals("COMPLETEDSETUP"))
                            completedSetup = true;
                        else
                            System.out.println(params[1]);
                        break;

                    case "NPLAYERS":
                        nPlayers = uInterface.getNPlayers();
                        //cli ritorna intero, da salvare in val
                        int val = 2;
                        try {
                            clientConnection.send(Integer.toString(nPlayers));
                        } catch (Exception e) {
                            canContinue = false;
                        }
                        break;
                    //TODO unire i due casi?

                    case "PLAYERINFO":
                        //ricevo array
                        playerInfo = uInterface.getPlayerInfo();

                        //ricevo reducedPlayer da cli
                        //invio stringa
                        //TODO concatenare senza sapere size
                        toSendMessage = playerInfo[0] + "|" + playerInfo[1];
                        try {
                            clientConnection.send(toSendMessage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "PLACEWORKER":
                        //TODO puo andare in loop
                        while (!completed) {
                            //ritorna intero
                            //Deve ritornare le coordinate della posizione in cui
                            //l'utente vuole posizionare il giocatore in questo formato
                            //x|y, 2|3, ritorna una stringa
                            toSendMessage = String.valueOf(uInterface.getPosition());
                            try {
                                clientConnection.send(toSendMessage);
                                completed = true;
                            } catch (Exception e) {
                                completed = false;
                            }
                        }
                        break;

                    case "GETNDIVINITIES":
                        List<String> divinitiesList = uInterface.getDivinities(Integer.parseInt(params[1]));
                        toSendMessage = gson.toJson(divinitiesList);
                        while (!completed) {
                            try {
                                clientConnection.send(toSendMessage);
                                completed = true;
                            } catch (Exception e) {
                                completed = false;
                            }
                        }
                        break;

                    case "GETDIVINITY":
                        divinitiesList = new ArrayList<String>(Arrays.asList(receivedMessage.split("\\|")));
                        divinitiesList.remove(0);
                        //ritorna la stringa contentente la divinita scelta
                        //passo come argomento la List<String> con le divinita tra cui scegliere
                        toSendMessage = uInterface.chooseDivinity(divinitiesList);
                        while (!completed) {
                            try {
                                clientConnection.send(toSendMessage);
                                completed = true;
                            } catch (Exception e) {
                                completed = false;
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exc");
                canContinue = false;
            }
        }

        int x, y, height;
        String piece, owner;
        //inizio partita
        while (canContinue) {
            try {
                receivedMessage = clientConnection.receive();
                if (receivedMessage.contains("|"))
                    params = receivedMessage.split("|");
                else
                    params[0] = receivedMessage;
            } catch (Exception e) {
                canContinue = false;
            }
            switch (params[0]) {
                case "NOTIFICATION":
                    System.out.println(params[1]);
                    break;

                case "PERFORMACTION":
                    completed = false;
                    toSendMessage = uInterface.performAction();
                    while (!completed) {
                        try {
                            clientConnection.send(toSendMessage);
                            completed = true;
                        } catch (Exception e) {
                            completed = false;
                        }
                    }
                    break;

                case "FINISHEDMATCH":
                    canContinue = false;
                    //perche finisce
                    break;

                case "UPDATE":
                    x = Integer.parseInt(params[1]);
                    y = Integer.parseInt(params[2]);
                    height = Integer.parseInt(params[3]);
                    if (!params[4].equals("")) {
                        piece = params[4];
                        if (piece.equals("WORKER")) {
                            owner = params[5];
                            modifyBoard(x, y, height, piece, owner);
                        } else
                            modifyBoard(x, y, height, piece);

                    } else
                        modifyBoard(x, y, height, "");


            }
        }
    }

    private void modifyBoard(int x, int y, int height, String piece) {
        board[x][y] = getCode(piece, height);
    }

    private void modifyBoard(int x, int y, int height, String piece, String owner) {
        board[x][y] = getCode(piece, height);
        //dare colore
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
}