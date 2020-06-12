package it.polimi.ingswPSP35.client;

import java.util.List;
import java.util.Map;

public class MatchInfo {
    private String username;
    private String colourText;
    private int colour;
    private Map<String, String> matchDivinities;
    private String playerDivinity;

    public void setColour(String colour)
    {
        colourText = colour;
        switch (colour)
        {
            case "RED":
                this.colour = 0;
                break;
            case "GREEN":
                this.colour = 1;
                break;
            case "BLUE":
                this.colour = 2;
                break;
        }
    }

    public void set(String username)
    {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getColour() {
        return colour;
    }

    public void set(int colour) {
        this.colour = colour;
    }

    public Map<String, String> getMatchDivinities() {
        return matchDivinities;
    }

    public void set(Map<String, String> matchDivinities) {
        playerDivinity = matchDivinities.get(username);
        matchDivinities.remove(username);
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
