package it.polimi.ingswPSP35.client;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.util.List;

public class MatchInfo {
    private String username;
    private int age;
    private String colourText;
    private int colour;
    private List<String> matchDivinities;
    private String playerDivinity;

    public void setColour(String colour)
    {
        colourText = colour;
        switch (colour)
        {
            case "RED":
                this.colour = 0;
                break;
            case "BLUE":
                this.colour = 1;
                break;
            case "GREEN":
                this.colour = 2;

        }
    }

    public void setPlayerDivinity(String playerDivinity)
    {
        this.playerDivinity = playerDivinity;
    }

    public void set(String username, int age)
    {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public int getColour() {
        return colour;
    }

    public void set(int colour) {
        this.colour = colour;
    }

    public List<String> getMatchDivinities() {
        return matchDivinities;
    }

    public void set(List<String> matchDivinities) {
        this.matchDivinities = matchDivinities;
    }

    public String getPlayerDivinity() {
        return playerDivinity;
    }

    public String print()
    {
        return "Your colour: " + colourText + "\n" +
                "Your divinity: " + playerDivinity;

    }
}
