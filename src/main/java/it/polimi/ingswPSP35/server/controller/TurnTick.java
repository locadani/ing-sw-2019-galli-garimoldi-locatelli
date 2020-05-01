/**
 * Handles every aspect of each turn
 */
package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.WinException;
import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.controller.divinities.Action;
import it.polimi.ingswPSP35.server.model.Player;

public class TurnTick {

    private AbstractTurn turn;

    public TurnTick()
    { }

    /**
     * Handles every aspect of each turn
     * @param player player who can perform moves
     */
    public void handleTurn(Player player) throws WinException
    {
     //   Thread defeatChecker = new Thread()
        turn = player.getDivinity().getTurn();
        RequestedAction chosenAction = null;
        boolean canContinue = true;
        do {
            chosenAction = View.performAction(player);
            canContinue = turn.tryAction(chosenAction.getAction(),chosenAction.getWorker(),chosenAction.getSquare());
        } while(!(canContinue && chosenAction.getAction()== Action.ENDTURN));
    }
}