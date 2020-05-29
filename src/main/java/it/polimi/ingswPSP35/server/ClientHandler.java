package it.polimi.ingswPSP35.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.server.controller.RequestedAction;
import it.polimi.ingswPSP35.server.model.Coordinates;
import it.polimi.ingswPSP35.server.model.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientHandler {
    private final Socket client;
    private Player player;
    private final LinkedBlockingQueue<Object> inboundMessages;
    private final LinkedBlockingQueue<Object> outboundMessages;
    private final Gson gson = new Gson();


    public ClientHandler(Socket client) {
        this.client = client;
        inboundMessages = new LinkedBlockingQueue<Object>();
        outboundMessages = new LinkedBlockingQueue<Object>();
        try {
            Thread reader = new Thread(new ServerReader(((client.getInputStream())), inboundMessages));
            reader.start();
            Thread writer = new Thread(new ServerWriter((new ObjectOutputStream(client.getOutputStream())), outboundMessages));
            writer.start();
        } catch (IOException e) {
            e.printStackTrace();
            //cannot get input stream
        }
        //TODO start pinging
        //setSocketTimeout
        createPlayer();
    }

    public Object getClientInput() {
        String message;
        try {
            message = (String) inboundMessages.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        String[] parts = message.split(":", 2);
        MessageID messageID = MessageID.valueOf(parts[0]);
        return deserialize(messageID, parts[1]);
    }

    public void sendNotificationToClient(String notification) {
        outboundMessages.add(MessageID.NOTIFICATION + ":" + notification);
    }

    public void sendObjectToClient(MessageID messageID, Object object) {
        String serializedObject = gson.toJson(object);
        serializedObject = messageID + ":" + serializedObject;
        outboundMessages.add(serializedObject);
    }

    public void createPlayer() {
        Object username = getClientInput();
        //TODO decide what to do with age parameter
        int age = (int) (Math.random()*100);
        //TODO handle same username
        player = new Player((String) username, age);
    }

    public int getNumberOfPlayers() {
        sendObjectToClient(MessageID.GETNUMBEROFPLAYERS, null);
        Object numberOfPlayers = getClientInput();
        if (numberOfPlayers instanceof Integer)
            return ((Integer) numberOfPlayers);
        else throw new IllegalArgumentException();
    }

    public Player getPlayer() {
        return player;
    }

    public Object deserialize(MessageID messageID, String jsonObject) {
        switch (messageID) {
            case GETNUMBEROFPLAYERS:
                return gson.fromJson(jsonObject, Integer.class);
            case CHOOSE2DIVINITIES:
            case CHOOSE3DIVINITIES:
                Type arrayList = new TypeToken<ArrayList<String>>() {}.getType();
                return gson.fromJson(jsonObject, arrayList);
            case PICKDIVINITY:
            case USERINFO:
                return gson.fromJson(jsonObject, String.class);
            case PERFORMACTION:
                return gson.fromJson(jsonObject, RequestedAction.class);
            case PLACEWORKER:
                return gson.fromJson(jsonObject, Coordinates.class);
        }
        throw new IllegalArgumentException();
    }
}
