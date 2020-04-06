package it.polimi.ingswPSP35.client.cli;

import java.util.*;

import server.model.Player;
import server.model.Board;
import server.model.Square;
import server.model.Piece;
import server.model.Block;
import server.model.Dome;
import server.model.Worker;
import server.model.Divinity;
import server.model.ConcreteDivinity;



/**
 * This is the class used by the client for the command line interface
 */
public class Cli {

    private Scanner input;

    /**
     * A string used for the username of the player
     */
    private String playername;

    private int playerage;


    public Cli(){
        input = new Scanner(System.in);
    }


    /**
     * Prints the welcome message
     */
    private void welcome(){
        String santoriniwelcome = "************************************************************\n" +
                                  "                                                             \n" +
                                  " Welcome To The Online Version of the board game Santorini\n" +
                                  "made by Paolo Galli, Tommaso Garimoldi and Daniele Locatelli\n" +
                                  "                                                             \n" +
                                  "**************************************************************\n";

        System.out.println(santoriniwelcome);

    }

    /**
     * Player settings for player 1 and number of players for the next game
     */
    private void firstplayer(){

        int numberofplayers = 0;

        welcome();
        System.out.println("Hello Player 1, please enter a nickname:\n");

        playername = input.nextLine();

        System.out.println("Type 2 if you want to play a two players match or 3 if you want to play a three players match:\n");

        numberofplayers = input.nextInt();

        while (numberofplayers < 2 || numberofplayers > 3){

            System.out.println("Format not valid please type 2 for a two players match or 3 for a three players match");
            numberofplayers = input.nextInt();
        }


        System.out.println("Now insert your age:\n");

        playerage = input.nextInt();



    }

    /**
     * Player settings for second and third players
     */
    private void genericplayer(){
        welcome();
        System.out.println("Hello new Player, please enter a nickname:\n");

        playername = input.nextLine();

        System.out.println("And your age:\n");

        playerage = input.nextInt();

        System.out.println("Now choose a color from the List below:\n");
     }


    /**
     * Connection configuration
     */
     private void onlineconfig(){

         boolean connected = false;

         //tryconnection();

         if (connected = true)
             System.out.println("Connection Established!");
          else {
              System.out.println("Something went wrong connection not established; please try again.");
              onlineconfig();
          }



         }

    /**
     * Manages a player's turn
     */
      private void turn(){
          boolean myturn = true;
          int action;

          while (myturn = true) {
              System.out.println("It's your turn");


              BoardPrinter.printboard();

              System.out.println("Choose between moving(0) or using your divinity power(1) if possible:\n");

              action = input.nextInt();
              while (action < 0 || action > 1){

                  System.out.println("Format not valid please type 0 to move or 1 to use your divinity power if possible:\n");
                  action = input.nextInt();
              }

              System.out.println("Choose a worker to move:\n");

              System.out.println("Choose a cell:\n");

              System.out.println("Where do you want to build?");






          }

      }





     }