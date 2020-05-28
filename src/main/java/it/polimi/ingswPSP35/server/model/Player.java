package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.server.VView.ReducedClasses.ReducedPlayer;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String username;
    private final int age;
    private Divinity divinity;
    private final ArrayList<Worker> workerList;
    private int colour;

    public Player(String username, int age) {
        this.username = username;
        this.age = age;
        workerList = new ArrayList<>();
        workerList.add(new Worker(this));
        workerList.add(new Worker(this));
    }

    public Player(ReducedPlayer rPlayer)
    {
        this.username = rPlayer.getUsername();
        this.age = rPlayer.getAge();
        workerList = new ArrayList<>();
        workerList.add(new Worker(this));
        workerList.add(new Worker(this));
    }

    public void setDivinity(Divinity d) {
        divinity = d;
    }

    public Divinity getDivinity() {
        return divinity;
    }

    public List<Worker> getWorkerList() {
        return List.copyOf(workerList);
    }

    public Worker getWorker(int i) {
        return workerList.get(i);
    }

    public void addWorker(Worker worker) {
        workerList.add(worker);
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public void setColour(int colour)
    {
        this.colour = colour;
    }

    public int getColour()
    {
        return colour;
    }

    public boolean isMyWorker(Coordinates worker)
    {
        for(Worker w: workerList)
        {
            if(w.getCoordinates().equals(worker))
                return true;
        }
        return false;
    }

    //TODO serve?
    public Player clone()
    {
        Player newPlayer = new Player(username, age);
        newPlayer.setDivinity(divinity);
        newPlayer.setColour(colour);
        newPlayer.setWorker(0,workerList.get(0).copy());
        newPlayer.setWorker(1,workerList.get(1).copy());
        return newPlayer;
    }

    public void setWorker(int i, Worker worker) {
        workerList.add(i, worker);
    }

}
