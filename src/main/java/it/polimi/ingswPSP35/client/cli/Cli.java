package it.polimi.ingswPSP35.client.cli;

import java.util.*;

import server.model.Player;
import server.model.Board;
import server.model.Cell;
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

    public Scanner input;

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
        String santoriniwelcome = "Welcome To The Online Version of the board game Santorini\n" +
                "made by Paolo Galli, Tommaso Garimoldi and Daniele Locatelli\n";

        System.out.println(santoriniwelcome);

    }

    /**
     * Client and player configuration
     */
    private void beginning(){
        welcome();
        System.out.println("Hello new Player, please insert your nickname:\n");

        playername = input.nextLine();

        System.out.println("And your age:\n");

        playerage = input.nextInt();

        System.out.println("Now choose a color from the List below:\n");
     }









}