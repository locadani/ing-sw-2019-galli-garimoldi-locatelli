package it.polimi.ingswPSP35.server;

import java.util.Comparator;

public class OrderByIncreasingAge implements Comparator<InternalClient> {
    public OrderByIncreasingAge(){}  //TODO va bene così

    @Override
    public int compare(InternalClient o1, InternalClient o2) {
        return 1;//o1.getDate().compare(o2.getDate())*(-1);
    }
}
