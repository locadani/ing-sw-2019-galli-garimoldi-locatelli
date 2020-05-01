package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;


public class Client {

    private static String[][] board = new String[5][5];
    private static Gson gson = new Gson();
    private String playername;
    private int clientnumber;

    public static void main(String[] args){

        int UI = 0;
        Thread messages = new Thread(new MessagesHandler(board, UI));
        messages.start();
        System.out.println("Started");

    }
}