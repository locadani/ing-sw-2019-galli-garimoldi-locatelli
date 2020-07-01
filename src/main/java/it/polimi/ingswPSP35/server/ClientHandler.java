package it.polimi.ingswPSP35.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingswPSP35.Exceptions.DisconnectedException;
import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.model.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientHandler {
    private final Socket clientSocket;
    private String username;
    private final LinkedBlockingQueue<Object> inboundMessages;
    private final LinkedBlockingQueue<Object> outboundMessages;
    private final Gson gson = new Gson();
    private final Thread reader;
    private final Thread writer;


    public ClientHandler(Socket clientSocket) throws IOException{
        this.clientSocket = clientSocket;
        inboundMessages = new LinkedBlockingQueue<Object>();
        outboundMessages = new LinkedBlockingQueue<Object>();

        reader = new Thread(new ServerReader(((clientSocket.getInputStream())), inboundMessages));
        reader.start();
        writer = new Thread(new ServerWriter((new ObjectOutputStream(clientSocket.getOutputStream())), outboundMessages));
        writer.start();


        clientSocket.setSoTimeout(6000);
        username = (String) getClientInput();
    }

    public Object getClientInput() throws DisconnectedException {
        String message;
        try {
            message = (String) inboundMessages.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        String[] parts = message.split(":", 2);
        MessageID messageID = MessageID.valueOf(parts[0]);
        if (messageID != MessageID.DISCONNECTED) {
            return deserialize(messageID, parts[1]);
        }
        else throw new DisconnectedException();
    }

    public void sendNotificationToClient(String notification) {
        outboundMessages.add(MessageID.NOTIFICATION + ":" + notification);
    }

    public void sendObjectToClient(MessageID messageID, Object object) {
        String serializedObject = gson.toJson(object);
        serializedObject = messageID + ":" + serializedObject;
        outboundMessages.add(serializedObject);
    }

    public int getNumberOfPlayers() throws DisconnectedException{
        sendObjectToClient(MessageID.GETNUMBEROFPLAYERS, null);
        Object numberOfPlayers = getClientInput();
        if (numberOfPlayers instanceof Integer)
            return ((Integer) numberOfPlayers);
        else throw new IllegalArgumentException();
    }

    public String getUsername() {
        return username;
    }

    public Player createPlayer() {
        return new Player(username, (int) (Math.random()*100));
    }

    public Object deserialize(MessageID messageID, String jsonObject) {
        switch (messageID) {
            case GETNUMBEROFPLAYERS:
            case CHOOSECOLOUR:
                return gson.fromJson(jsonObject, Integer.class);
            case CHOOSE2DIVINITIES:
            case CHOOSE3DIVINITIES:
                Type list = new TypeToken<ArrayList<String>>(){}.getType();
                return gson.fromJson(jsonObject, list);
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

    //TODO input and output stream might need to be closed
    public void disconnect() {
        try {
            System.out.println("Disconnecting user: " + this.getUsername());
            sendNotificationToClient("Disconnecting...");
            writer.interrupt();
            writer.join();
            clientSocket.close();
            reader.interrupt();
            reader.join();

            System.out.println("User " + this.getUsername() + " disconnected");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
