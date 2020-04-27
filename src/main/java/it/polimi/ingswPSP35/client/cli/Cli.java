package it.polimi.ingswPSP35.client.cli;

import java.util.*;


/**
 * This is the class used by the client for the command line interface
 */
public class Cli {

    private Scanner input;

    private final static List<String> divinities = new ArrayList<>(List.of("Apollo","Athena"));
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
     * @return
     */
    public int getNPlayers() {

        int numberofplayers = 0;

        welcome();

        System.out.println("Type 2 if you want to play a two players match or 3 if you want to play a three players match:\n");

        numberofplayers = input.nextInt();

        while (numberofplayers < 2 || numberofplayers > 3) {

            System.out.println("Format not valid please type 2 for a two players match or 3 for a three players match");
            numberofplayers = input.nextInt();
        }

        return numberofplayers;
    }

    public List<String> getDivinities(int numberofplayers){

        int value;
        List<String> choosenDivinities = new ArrayList<>();

        System.out.println("pick" +numberofplayers+ "divinities");

        for(int i = 0; i< divinities.size(); i++){
            System.out.println(i+": "+ divinities.get(i));
        }

        while(choosenDivinities.size()<numberofplayers) {

            value = input.nextInt();
            if (!choosenDivinities.contains(divinities.get(value)))
                choosenDivinities.add(divinities.get(value));

        }

        return choosenDivinities;
    }

    /**
     * Player settings for second and third players
     * @return
     */
    public String[] getplayerinfo(){
        welcome();
        String[] playerinfo = new String[2];

        System.out.println("Hello new Player, please enter a nickname:\n");

        playerinfo[0] = input.nextLine();

        System.out.println("And your age:\n");

        playerinfo[1] = String.valueOf(input.nextInt());

        System.out.println("Now choose a color from the List below:\n");

        return playerinfo;

     }

    /**
     * returns to the player his divinity and asks the player to place his workers on the board
     */
     public void divinityworkerplacer(){

        int cell0, cell1;

         System.out.println("this is your divinity:\n");

         //choosedivinity();

         Printer.printboard();
         System.out.println("select the cell for the first worker:\n");

         cell0 = input.nextInt();

         System.out.println("select the cell for the second worker:\n");

         cell1 = input.nextInt();
    }


    /**
     * Connection configuration
     */
     public void onlineconfig(boolean connected){

         if (connected)
             System.out.println("Connection Established!");
          else
              System.out.println("Something went wrong connection not established; please try again.");

     }

    /**
     * Manages a player's turn
     * @return boolean my turn for Client class
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

    /**
     * Prints the list of actions the player can do during his turn
     */
    private void getactionslist(){

          String actionslist = "move\n"+
                               "build\n"+
                               "godpower\n"+
                               "endturn\n";

        System.out.println(actionslist);
      }

    public void win(){
        String victory = "You won this game congratulations!\n";

        System.out.println(victory);
    }

    public void loss(){
        String loss = "You Lost";

       System.out.println(loss);
    }
}