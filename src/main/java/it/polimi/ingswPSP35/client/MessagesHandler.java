package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Collection;
import java.util.List;

import it.polimi.ingswPSP35.client.model.ReducedPlayer;

public class MessagesHandler implements Runnable{

    Gson gson = new Gson();
    ClientConnection clientConnection = null;
    Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;

    UInterface uInterface = new Cli();

    @Override
    public void run() {

        ReducedPlayer player = null;
        String receivedMessage;
        String toSendMessage = null;
        String[] params = new String[5];
        int workersPlaced = 0;
        boolean completed = false;
        boolean canContinue = true;
        int nPlayers;
        //connection
        String ip = "127.0.0.1";
        while(!completed) {
            try {
                socket = new Socket(ip, 7777);
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                clientConnection = new ClientConnection(input,output, socket);
                completed = true;
            } catch (IOException e) {
                completed = false;
            }
        }

        String[] playerInfo = new String[2];
        while(workersPlaced != 2)
        {
            try
            {
                receivedMessage = clientConnection.receive();
                System.out.println(receivedMessage);
                if(receivedMessage.contains("|"))
                    params = receivedMessage.split("|");
                else
                    params[0] = receivedMessage;

                switch(params[0]) {

                    case "NOTIFICATION":
                        System.out.println(params[1]);
                        break;

                    case "NPLAYERS":
                        nPlayers = uInterface.getNPlayers();
                        //cli ritorna intero, da salvare in val
                        int val = 2;
                        try {
                            clientConnection.send(Integer.toString(val));
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
                        try {
                            clientConnection.send(toSendMessage);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        break;

                    case "PLACEWORKER":
                        //TODO puo andare in loop
                        while(!completed) {
                            //ritorna intero
                            //Deve ritornare le coordinate della posizione in cui
                            //l'utente vuole posizionare il giocatore in questo formato
                            //x|y, 2|3, ritorna una stringa
                            toSendMessage = String.valueOf(uInterface.getPosition());
                            try {
                                clientConnection.send(toSendMessage);
                                completed = true;
                            }
                            catch (Exception e)
                            {
                                completed = false;
                            }
                        }
                        workersPlaced++;
                        break;

                    case "GETNDIVINITIES":
                        List<String> divinitiesList = uInterface.getDivinities(Integer.parseInt(params[1]));
                        toSendMessage = gson.toJson(divinitiesList);
                        while(!completed) {
                            try {
                                clientConnection.send(toSendMessage);
                                completed = true;
                            }
                            catch (Exception e)
                            {
                                completed = false;
                            }
                        }
                        break;

                    case "GETDIVINITY":
                        Type collectionType = new TypeToken<Collection<String>>(){}.getType();
                        divinitiesList = gson.fromJson(receivedMessage, collectionType);
                        //ritorna la stringa contentente la divinita scelta
                        //passo come argomento la List<String> con le divinita tra cui scegliere
                        toSendMessage = uInterface.chooseDivinity(divinitiesList);
                        while(!completed) {
                            try {
                                clientConnection.send(toSendMessage);
                                completed = true;
                            }
                            catch (Exception e)
                            {
                                completed = false;
                            }
                        }
                        break;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("Exc");
                canContinue = false;
            }
        }

        //inizio partita
        while(canContinue)
        {
            try
            {
                receivedMessage = clientConnection.receive();
                if(receivedMessage.contains("|"))
                    params = receivedMessage.split("|");
                else
                    params[0] = receivedMessage;
            }
            catch (Exception e)
            {
                canContinue = false;
            }
            switch(params[0])
            {
                case "NOTIFICATION":
                    System.out.println(params[1]);
                    break;

                case "PERFORMACTION":
                    completed = false;
                    toSendMessage = uInterface.performAction();
                    while(!completed) {
                        try {
                            clientConnection.send(toSendMessage);
                            completed = true;
                        }
                        catch (Exception e)
                        {
                            completed = false;
                        }
                    }
                    break;

                case "FINISHEDMATCH":
                    canContinue = false;
                    //perche finisce
            }
        }
    }
}