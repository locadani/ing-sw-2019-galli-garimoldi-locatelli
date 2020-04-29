package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;


public class Client {

    private static Gson gson = new Gson();
    private String playername;
    private int clientnumber;

    public static void main(String[] args){

        Thread messages = new Thread(new MessagesHandler());
        messages.start();
        System.out.println("Started");

    }
}