package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.client.cli.Cli;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    private Cli cli;

    private String playername;
    private int port, clientnumber;
    private Socket socket;


   public static void main(String[] args){

        Client client = new Client();
        Cli cli = new Cli();

        client.tryconnection();
        client.playerconfig();
        client.turn();

    }

    public Client(){

        this.clientnumber = clientnumber;
        this.port = port;
        this.socket = socket;
    }

    /**
     * Tries to connect to the server
     */
    private void tryconnection() {

        boolean connected;

        String ip = "127.0.0.1";
        Socket socket;
        try {
            socket = new Socket(ip, 7777);
            connected = true;
        } catch (IOException e) {
            connected = false;

        }

        cli.onlineconfig(connected);

    }

    /**
     * configurations of the players
     */
    private void playerconfig() {

        //server.getclientnumber

        if(clientnumber == 1)
            cli.firstplayer();
          else
              cli.genericplayer();


    }

    /**
     * manages the actions during a player's turn
     */
    public void turn(){

        boolean myturn = false;

        while (myturn)
        cli.turn(myturn);


    }

    public void choosedivinity() {

    }


}