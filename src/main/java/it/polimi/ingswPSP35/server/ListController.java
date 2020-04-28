/**
 * Used to get infos about what is contained in player list
 */

package it.polimi.ingswPSP35.server;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.List;
import java.util.Scanner;

public class ListController implements Runnable{
    List<Player> players;
    Scanner scanner = new Scanner(System.in);
    String input;
    Player player;


    public  ListController(List<Player> player)
    {
        this.players = player;
    }

    /**
     * Allows server to check what is containted in list
     */
    @Override
    public void run() {
        input = "!";
        while(!input.equals(""))
        {
                input = scanner.nextLine();
                System.out.printf("Player " + input + "'s name: ");
            try {
                player = players.get(Integer.parseInt(input) - 1);
                System.out.print("Username: " + player.getUsername() + " Age: " + player.getAge());
            }
            catch(Exception e)
            {
                System.out.println("Tutti i giocatori presenti sono questi");
            }
        }
    }
}
