package it.polimi.ingswPSP35.server;

import com.google.gson.Gson;
import it.polimi.ingswPSP35.server.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
            Thread reader = new Thread(new ServerReader(((ObjectInputStream) client.getInputStream()), inboundMessages));
            reader.start();
            Thread writer = new Thread(new ServerWriter(((ObjectOutputStream) client.getOutputStream()), outboundMessages));
            writer.start();
        } catch (IOException e) {
            e.printStackTrace();
            //cannot get input stream
        }
        //TODO start pinging
        //setSocketTimeout
        createPlayer();
    }

    public Object getClientInput(String expectedInput) {
        String message;
        try {
            message = (String) inboundMessages.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        String[] parts = message.split(":", 2);
        if (parts[0].equals(expectedInput)){
            return deserialize(parts[0]);
        }
    }

    public void sendToClient(Object object) {
        outboundMessages.add(object);
    }

    public void createPlayer() {
        //the first 2 strings sent from client are ALWAYS username and age
        Object username = getClientInput();
        Object age = getClientInput();
        player = new Player((String) username, (Integer) age);
    }

    public int getNumberOfPlayers() {
        //TODO network protocol
        sendToClient();
        Object numberOfPlayers = getClientInput();
        if (numberOfPlayers instanceof Integer)
            return ((Integer) numberOfPlayers);
        //send same request
        else
    }

    public Player getPlayer() {
        return player;
    }
}
