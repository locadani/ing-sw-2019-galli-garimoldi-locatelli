package it.polimi.ingswPSP35.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Reader implements Runnable {
    private final ObjectInputStream input;
    private static final String PING = "";
    private static final String FINISHEDSETUP = "FINISHEDSEUTP";
    private ScheduledExecutorService executorService;
    private boolean settingUp = true;

    public Reader(ObjectInputStream input) {
        this.input = input;
    }

    public void run() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        setup();
        playGame();
    }

    private void setup() {
        while (settingUp) {
            try {
                Object request = input.readObject();
                if (!request.equals(PING)) {
                    if (!request.equals(FINISHEDSETUP))
                        executorService.schedule(new SetupRequestHandler(request), 0, TimeUnit.SECONDS);
                    else settingUp = false;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void playGame() {
        while (true) {
            try {
                Object request = input.readObject();
                if (!request.equals(PING))
                    executorService.schedule(new GameRequestHandler(request), 0, TimeUnit.SECONDS);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
