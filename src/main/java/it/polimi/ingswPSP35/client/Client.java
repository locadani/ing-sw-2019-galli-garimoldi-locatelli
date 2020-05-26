package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;
import it.polimi.ingswPSP35.client.gui.Gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    private static Gson gson = new Gson();
    private String playername;
    private int clientnumber;

    public static void main(String[] args){
        String connectionInfo;
        initializeBoard();
        uInterface = new Cli();
       
        int UI = 0;
        Thread messages = new Thread(new UserAction(UI));
        messages.start();
        //TODO notifica inizio partita
    }
}