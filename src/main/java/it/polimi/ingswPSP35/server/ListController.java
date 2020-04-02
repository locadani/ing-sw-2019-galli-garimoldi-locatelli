package it.polimi.ingswPSP35.server;

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

    @Override
    public void run() {
        input = "!";
        while(!input.equals(""))
        {
            input = scanner.nextLine();
            switch(Integer.parseInt(input))
            {
                case 1:
                    System.out.println("Player 1's name: " + player.get(0).getPlayerName());
                    break;
                case 2:
                    System.out.println("Player 2's name: " + player.get(1).getPlayerName());
            }

        }
    }
}
