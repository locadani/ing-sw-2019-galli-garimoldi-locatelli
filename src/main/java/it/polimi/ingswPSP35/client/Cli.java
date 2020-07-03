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

    public Cli(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
        input = new Scanner(System.in);
        reducedBoard = new ReducedBoard();
        welcome();
    }


    private static void welcome() {
        String santoriniWelcome = "************************************************************\n" +
                "                                                             \n" +
                " Welcome To The Online Version of the board game Santorini\n" +
                "made by Paolo Galli, Tommaso Garimoldi and Daniele Locatelli\n" +
                "                                                             \n" +
                "**************************************************************\n";

        System.out.println(santoriniWelcome);

    }

    public void getNPlayers() {

        int numberOfPlayers;

        System.out.println("Type 2 if you want to play a two players match or 3 if you want to play a three players match:\n");

        numberOfPlayers = getValue(2, 3);

        networkHandler.send(MessageID.GETNUMBEROFPLAYERS, numberOfPlayers);

    }

    public String getPlayerInfo() {

        String playerInfo;

        System.out.println("Hello new Player, please enter a username:\n");
        playerInfo = input.nextLine();

        return playerInfo;
    }

    public void chooseColour(List<String> availableColors) {

        int chosenColor = 0;

        if (availableColors.size() != 1) {
            System.out.println("Now choose a color from the List below:\n");

            for (int i = 0; i < availableColors.size(); i++) {
                System.out.println(i + ": " + availableColors.get(i));
            }

            chosenColor = getValue(0, availableColors.size() - 1);
        }

        Printer.printBoard(reducedBoard.getMatrix());

        networkHandler.send(MessageID.CHOOSECOLOUR, chosenColor);
    }

    public void chosenColors(Map<String, String> chosenColors) {
        System.out.println("");
        for (Map.Entry<String, String> entry : chosenColors.entrySet()) {
            System.out.println(entry.getKey() + "'s color is " + entry.getValue().toLowerCase());
        }
        System.out.println("");
    }

    private void getDivinities(int numberOfPlayers, List<String> allDivinities) {

        int value;
        List<String> chosenDivinities = new ArrayList<>(numberOfPlayers);

        System.out.println("Pcick " + numberOfPlayers + " divinities");

        for (int i = 0; i < allDivinities.size(); i++) {
            System.out.println(i + ": " + allDivinities.get(i));
        }

        while (chosenDivinities.size() < numberOfPlayers) {

            value = getValue(0, allDivinities.size() - 1);
            if (!chosenDivinities.contains(allDivinities.get(value)))
                chosenDivinities.add(allDivinities.get(value));
        }

        networkHandler.send(MessageID.CHOOSE2DIVINITIES, chosenDivinities);
    }

    public void choose2Divinities(List<String> allDivinities) {
        getDivinities(2, allDivinities);
    }

    public void choose3Divinities(List<String> allDivinities) {
        getDivinities(3, allDivinities);
    }

    public void pickDivinity(List<String> divinitiesList) {

        int value;

        System.out.println("Choose your divinity:\n");

        for (int i = 0; i < divinitiesList.size(); i++) {
            System.out.println(i + ": " + divinitiesList.get(i));
        }

        value = getValue(0, divinitiesList.size() - 1);

        networkHandler.send(MessageID.PICKDIVINITY, divinitiesList.get(value));
    }

    public void chooseFirstPlayer(List<String> players) {
        int value;

        System.out.println("Choose the starting player:\n");

        for (int i = 0; i < players.size(); i++) {
            System.out.println(i + ": " + players.get(i));
        }

        value = getValue(0, players.size() - 1);

        networkHandler.send(MessageID.CHOOSEFIRSTPLAYER, value);
    }

    @Override
    public void setMatchInfo(Map<String, String> userToDivinity) {
        System.out.println("");
        for (Map.Entry<String, String> entry : userToDivinity.entrySet()) {
            System.out.println(entry.getKey() + "'s divinity is " + entry.getValue());
        }
        System.out.println("");
    }

    public void placeWorker() {
        int cell;

        //Printer.printboard();

        System.out.println("select the cell for the worker:\n");

        cell = getCell();

        networkHandler.send(MessageID.PLACEWORKER, new Coordinates(cell));
    }

    public void turnEnded() {
        displayNotification("Your turn has ended");
    }

    public void startMatch() {
        System.out.println("Game starts");
    }


    public void performAction() {

        RequestedAction requestedAction = null;
        int action, workernumber, cell;

        System.out.println("It's your turn");

        //Printer.printboard();

        System.out.println("Choose an action to do:\n");

        getactionslist();

        action = getValue(0, 3);

        switch (action) {

            case 0:
                System.out.println("Choose a worker to move:\n");

                workernumber = getCell();

                System.out.println("Choose a cell:\n");

                cell = getCell();
                requestedAction = new RequestedAction(workernumber, Action.MOVE, cell);
                break;

            case 1:
                System.out.println("Choose a worker to build with:\n");

                workernumber = getCell();

                System.out.println("Choose a cell:\n");

                cell = getCell();

                requestedAction = new RequestedAction(workernumber, Action.BUILD, cell);
                break;


            case 2:

                System.out.println("Choose a worker to use your god power with:\n");

                workernumber = getCell();

                System.out.println("Choose a cell:\n");

                cell = getCell();

                requestedAction = new RequestedAction(workernumber, Action.GODPOWER, cell);

                break;

            case 3:
                requestedAction = new RequestedAction(0, Action.ENDTURN, 0);
                break;

            case 4:
                requestedAction = new RequestedAction(0, Action.QUIT, 0);

        }

        networkHandler.send(MessageID.PERFORMACTION, requestedAction);
    }


    private void getactionslist() {

        String[] actionslist = {"move", "build", "godpower", "endturn"};

        for (int i = 0; i < actionslist.length; i++) {
            System.out.println(i + ": " + actionslist[i]);
        }

    }

    public String getConnectionInfo() {
        String ip;
        do {
            System.out.println("Insert IP address: ");
            ip = input.nextLine();
        } while (!correctIPAddress(ip));
        return ip;
    }


    private boolean correctIPAddress(String ip) {
        int value;
        String[] ipParts;

        if(ip.endsWith("."))
            ip = ip.substring(0,ip.length()-2);
        ipParts = ip.split("\\.");

        if (ipParts.length == 4) {
            for (String ipPart : ipParts) {
                try {
                    value = Integer.parseInt(ipPart);
                    if (value < 0 || value > 255)
                        return false;
                }
                catch (Exception e) {
                    return false;
                }
            }
        } else
            return false;
        return true;
    }

    public void displayNotification(String message) {
        System.out.println(message);
    }

    public void updateBoard(List<ReducedSquare> changedSquares) {
        reducedBoard.update(changedSquares);
        Printer.printBoard(reducedBoard.getMatrix());
    }


    private int getCell() {
        return getValue(1, 25);
    }

    //Min and max values are accepted
    private int getValue(int min, int max) {
        int value = 0;
        boolean accepted = false;

        do {
            try {
                value = input.nextInt();
                if (value >= min && value <= max)
                    accepted = true;
                else
                    System.out.println("Value out of range");
            }
            catch (InputMismatchException e) {
                System.out.println("Not accepted input format");
            }
            input.nextLine();


        } while (!accepted);

        return value;
    }
}

