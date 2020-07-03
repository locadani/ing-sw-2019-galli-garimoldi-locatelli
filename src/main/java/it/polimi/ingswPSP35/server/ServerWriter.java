package it.polimi.ingswPSP35.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerWriter implements Runnable {
    private final ObjectOutputStream output;
    private final LinkedBlockingQueue<Object> outboundMessages;

    public ServerWriter(ObjectOutputStream output, LinkedBlockingQueue<Object> outboundMessages) {
        this.output = output;
        this.outboundMessages = outboundMessages;
    }

    public void run() {
        while (true) {
            try {
                output.writeObject(outboundMessages.take());
            } catch (IOException e) {
                System.out.println("Disconnected");
            } catch (InterruptedException e) {
                flush();
                break;
            }
        }
    }

    private void flush() {
        //finish sending messages in buffer
        while (!outboundMessages.isEmpty()) {
            try {
                output.writeObject(outboundMessages.take());
            } catch (IOException | InterruptedException e) {
                System.out.println("Disconnected");
            }
        }
    }
}
