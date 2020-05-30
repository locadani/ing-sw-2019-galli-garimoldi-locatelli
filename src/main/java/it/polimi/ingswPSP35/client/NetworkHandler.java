package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkHandler {
    private Socket socket;
    private LinkedBlockingQueue<Object> outboundMessages;

    public void connect(String ip, String userInfo, UInterface userInterface) {
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
            send(MessageID.USERINFO + ":" + userInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Object message) {
        outboundMessages.add(message);
    }
}
