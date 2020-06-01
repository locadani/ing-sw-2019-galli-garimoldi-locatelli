package it.polimi.ingswPSP35.client;

import java.util.ArrayList;
import java.util.List;

public class Info {

    private int lastRequest;
    private int nPlayers;
    private String username;
    private int age;
    private List<String> chosenDivinities;
    private NetworkHandler observer;
    private String serverIp;
    private int serverPort;
    private String colour;
    private int firstWorkerPosition;
    private int secondWorkerPosition;


    public void setObserver(NetworkHandler observer)
    {
        this.observer = observer;
    }

    public String getIp()
    {
        return serverIp;
    }

    public int getPort()
    {
        return serverPort;
    }

    public String getChosenColour() {
        return colour;
    }

    public void setChosenColour(String colour) {
        this.colour = colour;
        update();
    }

    public void setWorkersPosition(int firstWorkerPosition, int secondWorkerPosition)
    {
        this.firstWorkerPosition = firstWorkerPosition;
        this.secondWorkerPosition = secondWorkerPosition;
    }

    public String getChosenDivinity() {
        return chosenDivinity;
    }

    public void setChosenDivinity(String chosenDivinity) {
        this.chosenDivinity = chosenDivinity;
        update();
    }

    private String chosenDivinity;

    public List<String> getChosenDivinities() {
        return chosenDivinities;
    }

    public void setPlayerInfo(String username, int age)
    {
        this.username = username;
        this.age = age;
        update();
    }

    public void setNPlayers(int nPlayers) {
        this.nPlayers = nPlayers;
        update();
    }

    public void setChosenDivinities(List<String> chosenDivinities)
    {
        this.chosenDivinities = new ArrayList<>(chosenDivinities);
        update();
    }


    public int getNPlayers()
    {
        return nPlayers;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    private void update()
    {
        //observer.update(this);
    }
}
