package it.polimi.ingswPSP35.client;

import com.google.gson.Gson;

import java.util.*;


/**
 * This is the class used by the client for the command line interface
 */
public class Cli implements UInterface {

    private final Scanner input;
    private final static List<String> divinities = new ArrayList<>(List.of("Apollo", "Artemis", "Athena", "Atlas", "Demeter", "Hephaestus", "Minotaur", "Pan", "Prometheus"));
    private ServerHandler serverHandler;
    private Board board;
    private Gson gson;

    public Cli(ServerHandler serverHandler, Board board)
    {
        gson = new Gson();
        this.serverHandler = serverHandler;
        this.board = board;
        input = new Scanner(System.in);
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

        serverHandler.update(Integer.toString(numberofplayers));

    }

    /**
     * Asks the first player to choose the divinites for the game
     *
     * @param numberofplayers is the number of players selected for the current match
      */
    public void getDivinities(int numberofplayers) {

        int value;
        List<String> choosenDivinities = new ArrayList<>();

        System.out.println("pick" + " " + numberofplayers + " " + "divinities");

        for (int i = 0; i < divinities.size(); i++) {
            System.out.println(i + ": " + divinities.get(i));
        }

        while (choosenDivinities.size() < numberofplayers) {

            value = input.nextInt();
            input.nextLine();
            if (!choosenDivinities.contains(divinities.get(value)))
                choosenDivinities.add(divinities.get(value));
        }

        String toSendMessage = gson.toJson(choosenDivinities);

        serverHandler.update(toSendMessage);
    }

    /**
     * Player settings for second and third players
     */
    public void getPlayerInfo() {
        welcome();
        String[] playerinfo = new String[2];

        System.out.println("Hello new Player, please enter a nickname:\n");
        playerinfo[0] = input.nextLine();

        System.out.println("And your age:\n");

        playerinfo[1] = String.valueOf(input.nextInt());
        input.nextLine();

        serverHandler.update(playerinfo[0] + ":" + playerinfo[1]);
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

        serverHandler.update(availableColors.get(choosencolor));
    }

    /**
     * returns to the player his divinity and asks the player to place his workers on the board
     */
    public void chooseDivinity(List<String> divinitiesList) {

        int value;

        System.out.println("Choose your divinity:\n");

        for (int i = 0; i < divinitiesList.size(); i++) {
            System.out.println(i + ": " + divinitiesList.get(i));
        }

        do {
            value = input.nextInt();
            input.nextLine();
        } while (value >= divinitiesList.size());

        serverHandler.update(divinitiesList.get(value));
    }


    /**
     * Asks the player to place the workers at the beginning of the game
    */
    public void getPosition() {
        int cell;

        //Printer.printboard();

        System.out.println("select the cell for the worker:\n");

        cell = input.nextInt();
        input.nextLine();

        serverHandler.update(Integer.toString(cell));
    }


    /**
     * Connection configuration
     */
    public void onlineconfig(boolean connected) {

        if (connected)
            System.out.println("Connection Established!");
        else
            System.out.println("Something went wrong connection not established; please try again.");

    }

    /**
     * Manages a player's turn
     */
    public void performAction() {

        String requestedAction = null;
        int action, workernumber, cell;

        System.out.println("It's your turn");

        //Printer.printboard();

        System.out.println("Choose an action to do:\n");

        getactionslist();

        action = input.nextInt();
        input.nextLine();

        switch (action) {

            case 0:
                System.out.println("Choose a worker to move:\n");

                workernumber = input.nextInt();
                input.nextLine();

                System.out.println("Choose a cell:\n");

                cell = input.nextInt();
                input.nextLine();

                requestedAction = workernumber + ":MOVE:" + cell;
                break;

            case 1:
                System.out.println("Choose a worker to build:\n");

                workernumber = input.nextInt();
                input.nextLine();

                System.out.println("Choose a cell:\n");

                cell = input.nextInt();
                input.nextLine();

                requestedAction = workernumber + ":BUILD:" + cell;
                break;


            case 2: {

                System.out.println("Choose an action to do:\n");

                getactionslist();

                action = input.nextInt();
                input.nextLine();
            }
            break;

            case 3:
                requestedAction = "0:ENDTURN:0";
                break;

            case 4:
                requestedAction = "0:QUIT:0";

        }

        serverHandler.update(requestedAction);
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
    public String getConnectionInfo() {
        String ip, connectionInfo;
        int port;
        System.out.println("Insert IP address: ");
        ip = input.nextLine();

      /*  System.out.println("Insert port: ");
        port = input.nextInt();
        input.nextLine();
        connectionInfo = ip + ":" + port;*/

        return ip;

    }

    public void notify(String message) {}

    public void update(String[] params) {
        board.update(params);
         Printer.printBoard(board.getMatrix());
    }

    @Override
    public void startMatch() {
        //setup
    }

    @Override
    public void configUI(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }
}