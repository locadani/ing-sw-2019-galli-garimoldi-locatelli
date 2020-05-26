/**
 * Handles every aspect of each turn
 */
package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.Exceptions.LossException;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnTick {

    private AbstractTurn turn;
    private final Winner winner;
    private final DefeatChecker defeatChecker;
    private Map <String, AbstractTurn> turns;

    public TurnTick(Winner winner, DefeatChecker defeatChecker, List<Player> players)
    {
        this.winner =  winner;
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
        turn = turns.get(player.getUsername());

        defeatChecker.checkDefeat(turn.copy(), player);
        canContinue = turn.tryAction(chosenAction.getWorker(), chosenAction.getAction(), chosenAction.getSquare());
        return canContinue;
    }
}