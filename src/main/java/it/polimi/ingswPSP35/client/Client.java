package it.polimi.ingswPSP35.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);

        String ip = "127.0.0.1";
        Socket server;
        try {
            server = new Socket(ip, 7777);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }

        try {
            ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(server.getInputStream());
            /* write a String to the server, and then get a String back */
            String str = null;
            while (!"".equals(str)) {
                str = (String) input.readObject();
                System.out.println(str);
                str= scanner.nextLine();
                output.writeObject(str);
            }
        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        scanner.nextLine();
        try {
            server.close();
        } catch (IOException e) { }
    }
}
