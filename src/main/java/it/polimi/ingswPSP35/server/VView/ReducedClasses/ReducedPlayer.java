/**
 * Contains info about Player useful to VView only
 */

package it.polimi.ingswPSP35.server.VView.ReducedClasses;

import it.polimi.ingswPSP35.server.model.Player;

public class ReducedPlayer {
    private String username;
    private String age;

    public ReducedPlayer(String username, String age)
    {
        this.age = age;
        this.username = username;
    }

    public ReducedPlayer (Player player)
    {
        this.age = player.getAge();
        this.username = player.getUsername();
    }

    /**
     * Converts ReducedPlayer class to Player
     * @return Converted ReducedPlayer
     */
    public Player toPlayer()
    {
        return new Player(username,age);
    }

    public String getUsername()
    {
        return username;
    }

    public String getAge()
    {
        return age;
    }

    public void printInfo()
    {
        System.out.println("User: " + username);
        System.out.println("Agge: " + age);
    }
}
