package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;
import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkHandler {
    private Socket socket;
    private Gson gson = new Gson();
    private LinkedBlockingQueue<Object> outboundMessages;

    public void connect(String ip, String username, UInterface userInterface) {
        try {
            socket = new Socket(ip, Server.SOCKET_PORT);
            //create reader thread
            LinkedBlockingQueue<String> inboundMessages = new LinkedBlockingQueue<>();
            Reader reader = new Reader(new ObjectInputStream(socket.getInputStream()), inboundMessages);
            //create request handler thread
            Thread requestHandler = new Thread(new RequestHandler(userInterface, inboundMessages));
            requestHandler.start();
            Thread readerThread = new Thread(reader);
            readerThread.start();

            //create writer thread
            outboundMessages = new LinkedBlockingQueue<Object>();
            Thread writer = new Thread(new Writer(new ObjectOutputStream(socket.getOutputStream()), outboundMessages));
            writer.start();
            send(MessageID.USERINFO, username);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(MessageID messageID, Object message) {
        String serializedObject = gson.toJson(message);
        serializedObject = messageID + ":" + serializedObject;
        outboundMessages.add(serializedObject);
    }
}
