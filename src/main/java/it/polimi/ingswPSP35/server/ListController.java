/**
 * Used to get infos about what is contained in player list
 */

package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.server.VView.InternalClient;

import java.util.List;
import java.util.Scanner;

public class ListController implements Runnable{
    List<InternalClient> player;
    Scanner scanner = new Scanner(System.in);
    String input;

    public  ListController(List<InternalClient> player)
    {
        this.player = player;
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
            System.out.println("Player " + input +"'s name: " + player.get(Integer.parseInt(input)-1).getPlayerName());

        }
    }
}
