package it.polimi.ingswPSP35.client;

import it.polimi.ingswPSP35.client.cli.Cli;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class FirstClient extends Client implements Runnable {

    private int port, clientnumber;
    private Socket socket;

    @Override
    public void run() {

        FirstClient firstClient = new FirstClient();

        //TODO runCli

    }

    public FirstClient(){

        this.clientnumber = 1;
        this.port = port;
        this.socket = socket;

    }


    @Override
    public void playerconfig() {

    }

    @Override
    public void choosedivinity() {

        //Vview.getdivinty();
        //return divinitylist;


    }
}
