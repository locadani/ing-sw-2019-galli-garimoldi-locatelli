package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    private static String[][] board = new String[5][5];
    private static Gson gson = new Gson();
    private String playername;
    private int clientnumber;

    public static void main(String[] args){

        int UI = 0;
        initializeBoard();
        Thread messages = new Thread(new UserAction(board, UI));
        messages.start();
        //TODO notifica inizio partita

    }

    private static void initializeBoard()
    {
        for(int i=0; i<25; i++)
            board[i/5][i%5] = "E";
    }
}