package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;
import it.polimi.ingswPSP35.Exceptions.DisconnectedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerHandler implements Runnable{

    private ClientConnection connection;
    private String[] params;
    private boolean canContinue;
    private boolean completedSetup;
    private List<String> divinitiesList;
    private UInterface uInterface;
    private Gson gson;

    public ServerHandler()
    {
        gson = new Gson();
    }

    public void initializeConnection(String ip, int port)
    {
        boolean completed;
        Socket socket;
        ObjectOutputStream output;
        ObjectInputStream input;
        ip = "127.0.0.1";
        port = 7777;
        try {
            socket = new Socket(ip, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            connection = new ClientConnection(input, output, socket);
        }
        catch (IOException e) {
            completed = false;
        }
    }

    public void update(List<String> message)
    {
        String toSend = gson.toJson(message);
        update(toSend);
    }

    public void update(String message)
    {
        System.out.println("Invio: " + message);
        try {
            connection.send(message);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupInterface(UInterface uInterface)
    {
        this.uInterface = uInterface;
    }


    /**
     * Waits for server request
     * @return parameters received from server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void run() {

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
                        uInterface.getNPlayers();
                        break;

                    case "PLAYERINFO":
                        uInterface.getPlayerInfo();
                        break;

                    case "CHOOSECOLOUR":
                        colours = new ArrayList<>(Arrays.asList(params));
                        colours.remove(0);
                        uInterface.chooseColour(colours);
                        break;

                    case "PLACEWORKER":
                        uInterface.getPosition();
                        break;

                    case "GETNDIVINITIES":
                        uInterface.getDivinities(Integer.parseInt(params[1]));
                        break;

                    case "GETDIVINITY":
                        divinitiesList = new ArrayList<String>(Arrays.asList(params));
                        divinitiesList.remove(0);

                        uInterface.chooseDivinity(divinitiesList);
                        break;

                    case "UPDATE":
                        uInterface.update(params);
                }

            }

            uInterface.startMatch();
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
                        uInterface.performAction();
                        break;

                    case "FINISHEDMATCH":
                        canContinue = false;
                        //perche finisce
                        break;

                    case "UPDATE":
                        uInterface.update(params);

                }
            }

        }
        catch (IOException e) {
            System.out.println("Somebody disconnected");
        }
    }

    private String[] receiveFromServer() throws DisconnectedException {
        String receivedMessage = null;
        String[] serverInfo = new String[1];

        do {
            try {
                receivedMessage = connection.receive();
                System.out.println("Ricevuto " + receivedMessage);

            }
            catch (IOException e) {
                throw new DisconnectedException("Client threw disconnEcc");
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (receivedMessage.equals("PING"));

        if (receivedMessage.contains(":"))
            serverInfo = receivedMessage.split(":");
        else
            serverInfo[0] = receivedMessage;
        return serverInfo;

    }
}