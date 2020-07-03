package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.commons.RequestedAction;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.server.model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for handling each {@code Player}'s turn. It can check whether a player has lost through
 * {@code DefeatChecker} and check the validity of moves requested by clients. <p>
 * Only one instance of this class is instantiated per game.
 */
public class TurnTick {

    private final DefeatChecker defeatChecker;
    private final Map <String, AbstractTurn> turns;

    /**
     * Sole constructor. Initializes the Map {@code turns} and {@code DefeatChecker}.
     * @param defeatChecker {@code DefeatChecker} instance for this game
     * @param players List of {@code Player}s necessary to retrieve the {@code AbstractTurn} of each player
     */
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
     * Attempts to perform an action of {@code player}. If the attempt is successful, it returns true.
     *
     * @param player {@code Player} attempting the action
     * @param chosenAction Action {@code player} is attempting
     * @return true if the attempt is successful
     */
    public boolean handleTurn(Player player, RequestedAction chosenAction)
    {
        AbstractTurn turn = turns.get(player.getUsername());

        return turn.tryAction(chosenAction.getWorker(), chosenAction.getAction(), chosenAction.getSquare());
    }

    /**
     * Checks if {@code Player} has lost. If that is the case, a {@code LossException} is thrown, otherwise the method
     * terminates with no side effects.
     *
     * @param player {@code Player} that may have lost
     * @throws LossException if {@code player} has lost
     */
    public void checkDefeat(Player player) throws LossException
    {
        defeatChecker.checkDefeat(turns.get(player.getUsername()).copy(), player);
    }
}