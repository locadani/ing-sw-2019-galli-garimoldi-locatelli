package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;
import it.polimi.ingswPSP35.commons.*;

import java.util.*;


/**
 * This is the class used by the client for the command line interface
 */
public class Cli implements UInterface {

    private final Scanner input;
    private NetworkHandler networkHandler;
    private ReducedBoard reducedBoard;
    private Gson gson;

    public Cli(NetworkHandler networkHandler)
    {
        gson = new Gson();
        this.networkHandler = networkHandler;
        input = new Scanner(System.in);
        reducedBoard = new ReducedBoard();
    }


    /**
     * Prints the welcome message
     */
    private static void welcome() {
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
    public void getNPlayers() {

        int numberofplayers;

        welcome();

        System.out.println("Type 2 if you want to play a two players match or 3 if you want to play a three players match:\n");

        numberofplayers = input.nextInt();
        input.nextLine();

        while (numberofplayers < 2 || numberofplayers > 3) {

            System.out.println("Format not valid please type 2 for a two players match or 3 for a three players match");
            numberofplayers = input.nextInt();
            input.nextLine();
        }

        networkHandler.send(MessageID.GETNUMBEROFPLAYERS, numberofplayers);

    }

    public void choose2Divinities(List<String> allDivinities) {
        getDivinities(2, allDivinities);
    }

    public void choose3Divinities(List<String> allDivinities) {
        getDivinities(3, allDivinities);
    }


    /**
     * Asks the first player to choose the divinites for the game
     *
     * @param numberofplayers is the number of players selected for the current match
      */
    private void getDivinities(int numberofplayers, List<String> allDivinities) {

        int value;
        List<String> choosenDivinities = new ArrayList<>(numberofplayers);

        System.out.println("pick " + numberofplayers + " divinities");

        for (int i = 0; i < allDivinities.size(); i++) {
            System.out.println(i + ": " + allDivinities.get(i));
        }

        while (choosenDivinities.size() < numberofplayers) {

            value = input.nextInt();
            input.nextLine();
            if (!choosenDivinities.contains(allDivinities.get(value)))
                choosenDivinities.add(allDivinities.get(value));
        }

        networkHandler.send(MessageID.CHOOSE2DIVINITIES, choosenDivinities);
    }

    /**
     * Player settings for second and third players
     */
    public String getPlayerInfo() {
        welcome();
        String[] playerinfo = new String[2];

        System.out.println("Hello new Player, please enter a nickname:\n");
        playerinfo[0] = input.nextLine();

       /* System.out.println("And your age:\n");

        playerinfo[1] = String.valueOf(input.nextInt());
        input.nextLine();*/

        return playerinfo[0];
    }

    /**
     * Asks the player to choose a colour to use for the next game
     *
     * @param availableColors the list of colors still available to select
      */
    public void chooseColour(List<String> availableColors) {

        int choosencolor;

        System.out.println("Now choose a color from the List below:\n");

        for (int i = 0; i < availableColors.size(); i++) {
            System.out.println(i + ": " + availableColors.get(i));
        }

        do {
            choosencolor = input.nextInt();
            input.nextLine();
        } while (choosencolor >= availableColors.size());

        networkHandler.send(MessageID.CHOOSECOLOUR , choosencolor);
    }

    /**
     * returns to the player his divinity and asks the player to place his workers on the board
     */
    public void pickDivinity(List<String> divinitiesList) {

        int value;

        System.out.println("Choose your divinity:\n");

        for (int i = 0; i < divinitiesList.size(); i++) {
            System.out.println(i + ": " + divinitiesList.get(i));
        }

        do {
            value = input.nextInt();
            input.nextLine();
        } while (value >= divinitiesList.size());

        networkHandler.send(MessageID.PICKDIVINITY, divinitiesList.get(value));
    }


    /**
     * Asks the player to place the workers at the beginning of the game
    */
    public void placeWorker() {
        int cell;

        //Printer.printboard();

        System.out.println("select the cell for the worker:\n");

        cell = input.nextInt();
        input.nextLine();

        networkHandler.send(MessageID.PLACEWORKER, new Coordinates(cell));
    }

    /**
     * Manages a player's turn
     */
    public void performAction() {

        RequestedAction requestedAction = null;
        int action, workernumber, cell;

        System.out.println("It's your turn");

        //Printer.printboard();

        System.out.println("Choose an action to do:\n");

        getactionslist();
        //TODO check input
        action = input.nextInt();
        input.nextLine();

        switch (action) {
            //TODO case 2,4, substitute string with RequestedAction class

            case 0:
                System.out.println("Choose a worker to move:\n");

                workernumber = input.nextInt();
                input.nextLine();

                System.out.println("Choose a cell:\n");

                cell = input.nextInt();
                input.nextLine();
                requestedAction = new RequestedAction(workernumber, Action.MOVE, cell);
                break;

            case 1:
                System.out.println("Choose a worker to build:\n");

                workernumber = input.nextInt();
                input.nextLine();

                System.out.println("Choose a cell:\n");

                cell = input.nextInt();
                input.nextLine();

                requestedAction = new RequestedAction(workernumber, Action.BUILD, cell);
                break;


            case 2: {

                System.out.println("Choose an action to do:\n");

                getactionslist();

                action = input.nextInt();
                input.nextLine();
            }
            break;

            case 3:
                requestedAction = new RequestedAction(0, Action.ENDTURN, 0);
                break;

            case 4:
                requestedAction = new RequestedAction(0, Action.QUIT, 0);

        }

        networkHandler.send(MessageID.PERFORMACTION, requestedAction);
  }


    /**
     * prints the waiting line
     */
    private void waitforyourturn() {

        System.out.println("waiting for your turn to start....");

    }

    /**
     * Prints the list of actions the player can do during his turn
     */
    private void getactionslist() {

        String[] actionslist = {"move", "build", "godpower", "endturn"};

        for (int i = 0; i < actionslist.length; i++) {
            System.out.println(i + ": " + actionslist[i]);
        }

    }

    /**
     * Prints the win message
     */
    public void win() {
        String victory = "You won this game congratulations!\n";

        System.out.println(victory);
    }

    /**
     * Prints the loss message
     */
    public void loss() {
        String loss = "You Lost";

        System.out.println(loss);
    }


    /**
     * Asks the player for the ip address and the port
   */
    //TODO implement getConnectionInfo
    public String getConnectionInfo() {
        return "127.0.0.1";
    }


    @Override
    public void startMatch() {
        System.out.println("Game starting...");
    }


    public void displayNotification(String message) {
        System.out.println(message);
    }

    public void updateBoard(List<ReducedSquare> changedSquares) {
         reducedBoard.update(changedSquares);
         Printer.printBoard(reducedBoard.getMatrix());
    }
}