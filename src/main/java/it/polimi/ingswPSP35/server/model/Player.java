package it.polimi.ingswPSP35.server.model;

import it.polimi.ingswPSP35.server.controller.divinities.Divinity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing each player. It has references to its corresponding {@code Divinity} and {@code Worker}s.
 * It also has all information of a player, such as username and color of workers.
 *
 * @author Paolo Galli
 *
 * @see Worker
 */
public class Player {
    private final String username;
    private final int age;
    private Divinity divinity;
    private final ArrayList<Worker> workerList;
    private int colour;

    /**
     * Sole constructor.
     * @param username name of the player
     * @param age age of the player
     */
    public Player(String username, int age) {
        this.username = username;
        this.age = age;
        workerList = new ArrayList<>();
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

}
