package it.polimi.ingswPSP35.client.gui;

import java.util.Scanner;

public class Update implements Runnable {

    private String message;
    private Scanner in;

    public Update(String message)
    {
        this.message = message;
        in = new Scanner(System.in);
    }
    @Override
    public void run() {

        while(true)
        {
            message = in.nextLine();
        }

    }
}
