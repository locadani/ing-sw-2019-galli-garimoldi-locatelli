package it.polimi.ingswPSP35.client;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServerPinger implements Runnable{

    private ObjectOutputStream outputStream;
    public ServerPinger(ObjectOutputStream outputStream)
    {
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        while(!Thread.interrupted())
        {
            try {
                outputStream.writeObject("PING");
            }
            catch (IOException e)
            {}
        }
    }
}
