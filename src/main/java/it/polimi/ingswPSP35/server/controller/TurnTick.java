/**
 * Handles every aspect of each turn
 */
package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnTick {

    private final DefeatChecker defeatChecker;
    private final Map <String, AbstractTurn> turns;

    public TurnTick(Winner winner, DefeatChecker defeatChecker, List<Player> players)
    {
        this.defeatChecker = defeatChecker;
        turns = new HashMap<String, AbstractTurn>();
        for(Player player : players)
        {
            turns.put(player.getUsername(), player.getDivinity().getTurn());
        }
    }

    /**
     * Handles every aspect of each turn
     * @param player player who can perform moves
     */
    public boolean handleTurn(Player player, RequestedAction chosenAction) throws LossException
    {
        boolean canContinue;
        AbstractTurn turn = turns.get(player.getUsername());

        defeatChecker.checkDefeat(turn.copy(), player);
        canContinue = turn.tryAction(chosenAction.getWorker(), chosenAction.getAction(), chosenAction.getSquare());
        return canContinue;
    }
}