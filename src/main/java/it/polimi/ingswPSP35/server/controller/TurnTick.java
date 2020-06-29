/**
 * Handles every aspect of each turn
 */
package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnTick {

    private final DefeatChecker defeatChecker;
    private final Map <String, AbstractTurn> turns;

    public TurnTick(DefeatChecker defeatChecker, List<Player> players)
    {
        this.defeatChecker = defeatChecker;
        turns = new HashMap<String, AbstractTurn>();
        for(Player player : players)
        {
            turns.put(player.getUsername(), player.getDivinity().getTurn());
        }
    }

    public boolean handleTurn(Player player, RequestedAction chosenAction) throws LossException
    {
        boolean canContinue;
        AbstractTurn turn = turns.get(player.getUsername());

        canContinue = turn.tryAction(chosenAction.getWorker(), chosenAction.getAction(), chosenAction.getSquare());
        return canContinue;
    }

    public void checkDefeat(Player player) throws LossException
    {
        defeatChecker.checkDefeat(turns.get(player.getUsername()).copy(), player);
    }
}