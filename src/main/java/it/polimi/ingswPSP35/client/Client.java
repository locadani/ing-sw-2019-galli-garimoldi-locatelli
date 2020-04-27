package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.client.cli.Cli;

import java.io.IOException;
import java.net.Socket;


public class Client {

    private Cli cli;

    private String playername;
    private static String[][] board = new String[5][5];
    private int port, clientnumber;
    private Socket socket;
    private boolean victory, loss;


   public static void main(String[] args){

        Client client = new Client();
        Cli cli = new Cli();

        client.tryconnection();
        client.playerconfig();
        cli.divinityworkerplacer();

        while (!client.victory && !client.loss)
        client.turn();

        client.endgame();

    }

    public Client(){

        this.clientnumber = clientnumber;
        this.port = port;
        this.socket = socket;
        this.board = board;
        this.victory = false;
        this.loss = false;
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
    private void turn(){

        boolean myturn = true;

        while (myturn)
         cli.turn(true);

    }

    /**
     * Manages the client board
     * @return the board for the printer class
     */
    public static String[][] clientboard(){

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                board[i][j] = "E";
            }
        }

        return board;

    }

    private void endgame(){

       if(victory && !loss)
           cli.win();
        else if(loss && !victory)
            cli.loss();
    }


}