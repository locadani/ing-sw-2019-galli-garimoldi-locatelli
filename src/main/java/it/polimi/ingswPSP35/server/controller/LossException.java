package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.model.Player;

public class LossException extends Exception{
    Player loser;
    LossException (Player player){
        this.loser = player;
    }
}
