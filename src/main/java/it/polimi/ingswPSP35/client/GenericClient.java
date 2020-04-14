package it.polimi.ingswPSP35.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class GenericClient extends Client  implements Runnable{

    private int port, clientnumber;
    private Socket socket;


    @Override
    public void run() {

        GenericClient genericClient = new GenericClient();

        //TODO runCLi

    }

    public GenericClient(){

        this.clientnumber = clientnumber;
        this.port = port;
        this.socket = socket;
    }

    @Override
    public void playerconfig() {

    }

    @Override
    public void choosedivinity() {

      //Vview.getdivinity()
      //return divinitylist;
    }

}

