package it.polimi.ingswPSP35.client.cli;

import java.util.*;


/**
 * This is the class used by the client for the command line interface
 */
public class Cli {

    private Scanner input;


    private String playername;

    private int playerage;


    public Cli(){
        input = new Scanner(System.in);
    }


    /**
     * Prints the welcome message
     */
    private static void welcome(){
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
    public void firstplayer(){

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

        System.out.println("pick" +numberofplayers+ "divinities");

        //choosedivinity();

    }

    /**
     * Player settings for second and third players
     */
    public void genericplayer(){
        welcome();
        System.out.println("Hello new Player, please enter a nickname:\n");

        playername = input.nextLine();

        System.out.println("And your age:\n");

        playerage = input.nextInt();

        System.out.println("Now choose a color from the List below:\n");

        System.out.println("pick a divinity:\n");

        //choosedivinity();
     }


    /**
     * Connection configuration
     */
     public void onlineconfig(boolean connected){

         if (connected = true){
             System.out.println("Connection Established!");

         }
          else {
              System.out.println("Something went wrong connection not established; please try again.");

          }



         }

    /**
     * Manages a player's turn
     * @return
     */
    public boolean turn(boolean myturn){
          String action;
          int workernumber, cell;

          System.out.println("It's your turn");

              Printer.printboard();

              System.out.println("Choose an action to do:\n");

              getactionslist();

              action = input.nextLine();

              while (action != "endturn") {
                  switch (action) {

                      case "move": {
                          System.out.println("Choose a worker to move:\n");

                          workernumber = input.nextInt();

                          System.out.println("Choose a cell:\n");

                          cell = input.nextInt();

                          System.out.println("Choose an action to do:\n");

                          Printer.printboard();

                          getactionslist();

                          action = input.nextLine();
                      }

                      case "build": {
                          System.out.println("In which cell do you want to build?\n");

                          Printer.printboard();
                          cell = input.nextInt();

                          System.out.println("Choose an action to do:\n");

                          getactionslist();

                          action = input.nextLine();
                      }

                      case "godpower": {

                          System.out.println("Choose an action to do:\n");

                          getactionslist();

                          action = input.nextLine();
                      }

                      case "endturn":{
                          myturn = false;
                          waitforyourturn();
                      }
                  }
              }
              return myturn;

    }

      private void waitforyourturn(){

        System.out.println("waiting for your turn to start....");

      }

      private void getactionslist(){

        //getstheactionlist
      }




     }