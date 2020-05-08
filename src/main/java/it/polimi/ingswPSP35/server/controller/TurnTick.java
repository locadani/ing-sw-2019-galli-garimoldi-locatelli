/**
 * Handles every aspect of each turn
 */
package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.PlayerQuitException;
import it.polimi.ingswPSP35.Exceptions.WinException;
import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.controller.divinities.Action;
import it.polimi.ingswPSP35.server.model.Player;

public class TurnTick {

    private AbstractTurn turn;
    private Winner winner;

    public TurnTick(Winner winner)
    {
        this.winner =  winner;
    }

    /**
     * Handles every aspect of each turn
     * @param player player who can perform moves
     */
    public void handleTurn(Player player) throws PlayerQuitException
    {
        turn = player.getDivinity().getTurn();
        RequestedAction chosenAction = null;
        boolean canContinue = false;
        do {
            chosenAction = View.performAction(player);
            if(chosenAction.getAction()== Action.QUIT)
                throw new PlayerQuitException(player);
            canContinue = turn.tryAction(chosenAction.getWorker(), chosenAction.getAction(), chosenAction.getSquare());
            if(canContinue)
            View.notify(player,"Action Successful");
            else
                View.notify(player, "Action Not Successful");
        } while(!(canContinue && chosenAction.getAction()== Action.ENDTURN) && winner.getWinner() != null);
        if (winner.getWinner() == null)
            View.notify(player, "Turn Finished");
    }


}