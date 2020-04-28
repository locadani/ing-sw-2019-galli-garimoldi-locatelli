package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.server.VView.InternalClient;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.Comparator;

public class OrderByIncreasingAge implements Comparator<Player> {
    public OrderByIncreasingAge(){}

    @Override
    public int compare(Player o1, Player o2) {
        if(Integer.parseInt(o1.getAge())>Integer.parseInt(o2.getAge()))
            return 1;
        return -1;
    }
}
