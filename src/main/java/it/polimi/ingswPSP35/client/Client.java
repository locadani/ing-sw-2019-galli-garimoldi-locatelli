package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.client.cli.Cli;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public abstract class Client {

   private String playername;
   private int port;
   private Socket socket;



    /**
     * Tries to connect to the server
     */
    public static boolean tryconnection(boolean connected) {

        String ip = "127.0.0.1";
        Socket socket;
        try {
            socket = new Socket(ip, 7777);
            connected = true;
        } catch (IOException e) {
            connected = false;

        }
      return connected;
    }

    /**
     * configurations of the players
     */
    public abstract void playerconfig();

    /**
     * manages the actions during a player's turn
     */
    public static void action(){

        //getactionlist();
        //return actionlist();


    }

    public abstract void choosedivinity();


}