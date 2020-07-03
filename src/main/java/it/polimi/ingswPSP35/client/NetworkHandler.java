package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;
import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.commons.Pinger;
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
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            Thread writer = new Thread(new Writer(outputStream, outboundMessages));
            writer.start();

            //create pinger thread
            Thread pinger = new Thread(new Pinger(outboundMessages));
            pinger.start();

            socket.setSoTimeout(6000);

            send(MessageID.USERINFO, username);
        } catch (IOException e) {
            System.out.println("Disconnected");
        }
    }

    public void send(MessageID messageID, Object message) {
        String serializedObject = gson.toJson(message);
        serializedObject = messageID + ":" + serializedObject;
        outboundMessages.add(serializedObject);
    }
}
