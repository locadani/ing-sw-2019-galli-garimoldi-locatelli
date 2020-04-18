/**
 * Handles every aspect of each turn
 */

package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.model.Player;

public class TurnTick {
    public TurnTick()
    {}

    /**
     * Handles every aspect of each turn
     * @param player player who can perform moves
     */
    public void handleTurn(Player player)
    {
        RequestedAction chosenAction;
        boolean canContinue = true;
        while(canContinue) {
            chosenAction = View.performAction(player);
            canContinue = player.getDivinity().tryAction(chosenAction);
        }
    }
}
