package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.Exceptions.LossException;
import it.polimi.ingswPSP35.commons.Coordinates;
import it.polimi.ingswPSP35.server.controller.divinities.AbstractTurn;
import it.polimi.ingswPSP35.commons.Action;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for checking whether a player has lost or not. It has only one public method that throws
 * a {@code LossException} if the {@code Player} it's checking cannot perform any valid moves. <p>
 *
 * This class is initialized once for each game by {@code GameDirector} and is handled by {@code TurnTick}.
 *
 * @author Paolo Galli
 * @see GameDirector
 * @see TurnTick
 */

//NOTE modifying the mediator during simulation might cause problems down the line
public class DefeatChecker {
    //divinityList is initialized during setup with a copy of each divinity
    private final List<Player> playerList;
    private final Board board;

    /**
     * Sole constructor.
     *
     * @param playerList List of players in this match
     * @param board Reference to the {@code Board} instance shared by all divinities in this match
     */
    public DefeatChecker(List<Player> playerList, Board board) {
        this.playerList = playerList;
        this.board = board;
    }

    /**
     * Checks whether {@code player} can perform a valid action in the current state of {@code turn}. If no valid actions
     * can be performed, a {@code LossException} is thrown, otherwise the method terminates with no side effects as soon
     * as a valid action is found.
     *
     * @param turn turn corresponding to {@code Player}'s divinity
     * @param player {@code Player} that may have lost
     * @throws LossException if no valid actions for {@code player} are found
     */
    public void checkDefeat(AbstractTurn turn, Player player) throws LossException {
        Board boardAlias = new Board(board);
        Divinity currentDivinity = player.getDivinity();

        //change board reference to avoid side effects
        currentDivinity.setBoard(boardAlias);
        //save reference to selected worker to restore game state after this method terminates
        Worker originalSelectedWorker = currentDivinity.getSelectedWorker();

        //if it's the first action of the turn, simulate turns for both workers
        if (turn.getActionsTaken().isEmpty()) {
            for (Worker worker : player.getWorkerList()) {
                if (simulate(turn, worker.getCoordinates(), boardAlias)) {
                    //restore divinity state
                    currentDivinity.setBoard(board);
                    return;
                }
            }
        }
        //simulate only the selected worker
        else {
            currentDivinity.selectWorker(originalSelectedWorker.getCoordinates());
            if (simulate(turn, originalSelectedWorker.getCoordinates(), boardAlias)) {
                //restore divinity state
                currentDivinity.setBoard(board);
                currentDivinity.selectWorker(originalSelectedWorker.getCoordinates());
                return;
            }
            //restore divinity state partially
            currentDivinity.selectWorker(originalSelectedWorker.getCoordinates());
        }
        //if no valid actions are found, restore divinity state and throw exception
        currentDivinity.setBoard(board);
        if (originalSelectedWorker != null)
            currentDivinity.selectWorker(originalSelectedWorker.getCoordinates());
        playerList.remove(player);
        throw new LossException(player);
    }

    //simulates all possible actions in "turn", returns true as soon as a valid action is found.
    //if no valid actions are found returns false
    private boolean simulate(AbstractTurn turn, Coordinates workerCoordinates, Board b) {
        //if availableActions contains ENDTURN, a valid action has been found
        if (turn.getAvailableActions().contains(Action.ENDTURN))
            return true;
        //attempt all available actions
        for (Action action : turn.getAvailableActions()) {
            //NOTE: ONLY WORKS FOR GODPOWERS WHICH ARE CALLED EXPLICITLY AND ONLY AFFECT ADJACENT SQUARES
            //attempt each action for every adjacent square
            for (Square s : getAdjacentSquares(workerCoordinates, b)) {
                //NOTE: there is no need to copy board or turn as they only get modified once a successful action is found
                //if a valid action has been found, return true
                if (turn.tryAction(workerCoordinates, action, s.getCoordinates())) {
                    return true;
                }
            }
        }
        return false;
    }

    //returns a List containing all squares adjacent to the square on Board b identified by "coordinates"
    private List<Square> getAdjacentSquares(Coordinates coordinates, Board b) {
        int sX = coordinates.getR();
        int sY = coordinates.getC();
        List<Square> adjacentSquares = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            int cX = sX + incrementsForAdjacentSquares[i][0];
            int cY = sY + incrementsForAdjacentSquares[i][1];
            //check if it's in bounds of board
            if ((cX >= 0) && (cX <= 4) && (cY >= 0) && (cY <= 4)) {
                adjacentSquares.add(b.getSquare(cX, cY));
            }
        }
        return adjacentSquares;
    }

    private static final Integer[][] incrementsForAdjacentSquares = {
            {-1, -1},
            {0, -1},
            {1, -1},
            {1, 0},
            {1, 1},
            {0, 1},
            {-1, 1},
            {-1, 0}
    };
}