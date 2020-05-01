package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.server.controller.divinities.Divinity;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String username;
    private final int age;
    private Divinity divinity;
    private ArrayList<Worker> workerList;
    private int colour;

    public Player(String username, int age) {
        this.username = username;
        this.age = age;
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

    public void setWorker(int i, Worker worker) {
        workerList.add(i, worker);
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }
}
