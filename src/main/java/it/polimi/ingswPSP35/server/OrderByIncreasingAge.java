package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.server.VView.InternalClient;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.Comparator;

public class OrderByIncreasingAge implements Comparator<Player> {
    public OrderByIncreasingAge(){}  //TODO va bene così

    @Override
    public int compare(Player o1, Player o2) {
        return 1;//o1.getDate().compare(o2.getDate())*(-1);
    }
}
