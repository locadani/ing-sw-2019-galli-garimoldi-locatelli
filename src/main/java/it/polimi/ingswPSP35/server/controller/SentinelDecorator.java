package it.polimi.ingswPSP35.server.controller;

/**
 * Decorator used to allow dynamic decoration of the {@code DivinityMediator}.
 * Since instances of {@code DivinityMediatorDecorator} are stored similarly to a singly-linked list, this class is used
 * as a "head" pointer of this list: without it, the last decorator could not be removed.
 *
 * @author Paolo Galli
 * @see DivinityMediator
 * @see DivinityMediatorDecorator
 */

public class SentinelDecorator extends DivinityMediatorDecorator{
    public SentinelDecorator(DivinityMediator d) {
        super(d);
    }

    @Override
    public String getName() {
        return "Sentinel";
    }
}
