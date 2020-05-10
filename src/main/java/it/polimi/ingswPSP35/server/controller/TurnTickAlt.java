package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.controller.divinities.Action;
import it.polimi.ingswPSP35.server.model.Player;

public class TurnTickAlt {

    private AbstractTurn turn;
    private DefeatCheckerAlt defeatChecker;

    public TurnTickAlt(DefeatCheckerAlt df)
    {
        this.defeatChecker = df;
    }

    /**
     * Handles every aspect of each turn
     * @param player player who can perform moves
     */
    public Player handleTurn(Player player) throws LossException
    {
        //   Thread defeatChecker = new Thread()
        turn = player.getDivinity().getTurn();
        RequestedAction chosenAction = null;
        boolean canContinue = true;
        do {
            Player loser = defeatChecker.checkDefeat(turn.copy(), player);
            if (loser == null) {
                chosenAction = View.performAction(player);
                canContinue = turn.tryAction(chosenAction.getAction(), chosenAction.getWorker(), chosenAction.getSquare());
            }
            else return loser;
        } while(!(canContinue && chosenAction.getAction()== Action.ENDTURN));
        return null;
    }
}
