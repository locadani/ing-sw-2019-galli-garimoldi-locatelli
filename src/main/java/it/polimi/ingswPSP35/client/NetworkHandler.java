package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkHandler {
    private Socket socket;
    private LinkedBlockingQueue<Object> outboundMessages;


    public void connect(String ip, String userInfo) {
        try {
            Socket socket = new Socket(ip, Server.SOCKET_PORT);
            Thread reader = new Thread(new Reader((ObjectInputStream) socket.getInputStream()));
            reader.start();
            outboundMessages = new LinkedBlockingQueue<Object>();
            Thread writer = new Thread(new Writer((ObjectOutputStream) socket.getOutputStream(), outboundMessages));
            writer.start();
            send(userInfo);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Object message) {
        outboundMessages.add(message);
    }
}
