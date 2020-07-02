package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.commons.ReducedSquare;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestHandler implements Runnable {
    UInterface userInterface;
    LinkedBlockingQueue<String> inboundMessages;
    Gson gson = new Gson();

    public RequestHandler(UInterface userInterface, LinkedBlockingQueue<String> inboundMessages) {
        this.userInterface = userInterface;
        this.inboundMessages = inboundMessages;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String request = inboundMessages.take();
                handleRequest(request);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void handleRequest(String message) {
        String[] parts = message.split(":", 2);
        MessageID messageID = MessageID.valueOf(parts[0]);
        String serializedObject = parts[1];
        switch (messageID) {
            case GETNUMBEROFPLAYERS:
                userInterface.getNPlayers();
                break;
            case CHOOSE2DIVINITIES:
                Type list = new TypeToken<List<String>>() {}.getType();
                userInterface.choose2Divinities(gson.fromJson(serializedObject, list));
                break;
            case CHOOSE3DIVINITIES:
                list = new TypeToken<List<String>>() {}.getType();
                userInterface.choose3Divinities(gson.fromJson(serializedObject, list));
                break;
            case PLACEWORKER:
                userInterface.placeWorker();
                break;
            case UPDATE:
                Type squareList = new TypeToken<List<ReducedSquare>>() {}.getType();
                userInterface.updateBoard(gson.fromJson(serializedObject, squareList));
                break;
            case PICKDIVINITY:
                list = new TypeToken<List<String>>() {}.getType();
                userInterface.pickDivinity(gson.fromJson(serializedObject, list));
                break;
            case PERFORMACTION:
                userInterface.performAction();
                break;
            case CHOOSECOLOUR:
                list = new TypeToken<List<String>>() {}.getType();
                userInterface.chooseColour(gson.fromJson(serializedObject, list));
                break;
            case DIVINITIESCHOSEN:
                Type map = new TypeToken<Map<String, String>>() {}.getType();
                userInterface.setMatchInfo(gson.fromJson(serializedObject, map));
                break;
            case FINISHEDSETUP:
                userInterface.startMatch();
                break;
            case NOTIFICATION:
                userInterface.displayNotification(serializedObject);
                break;
            case CHOOSEFIRSTPLAYER:
                list = new TypeToken<List<String>>() {}.getType();
                userInterface.chooseFirstPlayer(gson.fromJson(serializedObject, list));
                break;
            case TURNENDED:
                userInterface.turnEnded();
                break;
            case CHOSENCOLORS:
                map = new TypeToken<Map<String, String>>() {}.getType();
                userInterface.chosenColors(gson.fromJson(serializedObject, map));
        }
    }
}
