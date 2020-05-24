package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.client.gui.GUI;
import it.polimi.ingswPSP35.client.gui.GuiT;
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


    //classe che contiene board, info giocatore, divinita, colore. Questa viene passata alla UI

    private Gson gson = new Gson();
    private ClientConnection clientConnection = null;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private UInterface uInterface;
    private Board board;
    private ServerPinger pinger;
    private Thread pingerThread;

    public UserAction(int UI) {
        if (UI == 0)
            uInterface = new GuiT();
        this.board = new Board();
    }

    @Override
    public void run() {

        String toSendMessage;
        String[] params;
        boolean completed;
        boolean canContinue;
        boolean completedSetup;
        int nPlayers;
        String connectionInfo;


        do {
            connectionInfo = uInterface.getConnectionInfo();
            params = connectionInfo.split(":");
            completed = connectionSetup(params[0], Integer.parseInt(params[1]));
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
                        System.out.println("Sent message: " + toSendMessage);
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
                        board.update(params);
                        uInterface.update(board); //meglio passarla come costruttore?
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
                        board.update(params);

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
     * Waits for server request
     * @return parameters received from server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private String[] receiveFromServer() throws DisconnectedException {
        String receivedMessage = null;
        String[] serverInfo = new String[1];

        do {
            try {
                receivedMessage = clientConnection.receive();

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

}