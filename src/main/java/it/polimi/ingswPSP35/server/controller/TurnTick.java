/**
 * Handles every aspect of each turn
 */
package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.Exceptions.PlayerQuitException;
import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.controller.divinities.Action;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Square;

import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * Handles every aspect of each turn
     * @param player player who can perform moves
     */
    public boolean handleTurn(Player player, RequestedAction chosenAction) throws LossException
    {
        AbstractTurn turn = turns.get(player.getUsername());

        defeatChecker.checkDefeat(turn.copy(), player);
        boolean actionSuccesful = turn.tryAction(chosenAction.getWorker(), chosenAction.getAction(), chosenAction.getSquare());
        if (actionSuccesful) defeatChecker.checkIfAllPlayersHaveWorkers();
        return actionSuccesful;
    }
}