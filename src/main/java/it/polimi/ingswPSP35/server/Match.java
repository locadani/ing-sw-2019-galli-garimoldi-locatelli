package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.server.Exceptions.LossException;
import it.polimi.ingswPSP35.server.Exceptions.PlayerQuitException;
import it.polimi.ingswPSP35.server.VView.ReducedClasses.ReducedPlayer;
import it.polimi.ingswPSP35.server.VView.View;
import it.polimi.ingswPSP35.server.controller.*;
import it.polimi.ingswPSP35.server.controller.divinities.Action;
import it.polimi.ingswPSP35.server.controller.divinities.Divinity;
import it.polimi.ingswPSP35.server.model.Board;
import it.polimi.ingswPSP35.server.model.Coordinates;
import it.polimi.ingswPSP35.server.model.Player;

import java.io.IOException;
import java.util.*;

public class Match{

    private TurnTick turnTick;
    private Board board;
    private int nPlayers;
    private List<Player> players;
    private List<String> chosenDivinities;
    private DivinityMediator divinityMediator;
    private Winner winner;
    private DefeatChecker defeatChecker;
    private View view;
    private final List<String> colours = new ArrayList<>(List.of("RED", "BLUE", "GREEN"));

    public Match(List<ReducedPlayer> rPlayers, View view)
    {

        for(ReducedPlayer player : rPlayers)
        {
            players.add(new Player(player));
        }
        board = new Board();
        turnTick = null;
        players = new ArrayList<>();
        chosenDivinities = new ArrayList<>();
        divinityMediator = null;
        winner = new Winner();
        //settings
        players.sort(new OrderByIncreasingAge());
        this.view = view;
    }

    public void startMatch() throws PlayerQuitException {
        try {
            matchSetup();
            beginMatch();
        }
        catch (IOException e)
        {}
    }

    private void matchSetup() throws IOException
    {

        String message;
        chooseMatchDivinities();

        choosePlayerDivinity();

        message = "Chosen divinites:\n";
        for(Player p: players)
        {
            message = message + p.getUsername() + " chose " + p.getDivinity().getName()+ "\n";
        }
        view.notify(players, message);

        setDivinityMediator();

        //TODO fare copia lista giocatori (abstract turn)
        defeatChecker = new DefeatChecker(listDeepCopy(players),divinityMediator);

        //place Workers and set colour
        for (Player player : players) {

            chooseColour(player);

            placeWorker(0,player);
            placeWorker(1,player);
        }

        view.notify(players, "COMPLETEDSETUP");
    }

    private void beginMatch() throws PlayerQuitException
    {
        Player current;
        Iterator<Player> playerIterator = players.iterator();
        //Game starts
        turnTick = new TurnTick(winner, defeatChecker, players);

        while (winner.getWinner() == null) {
            try {

                current = playerIterator.next();
                playTurn(current);

                if (!playerIterator.hasNext())
                    playerIterator = players.iterator();
            }
            catch (LossException e)
            {
                deletePlayer(e.getLoser());
            }
        }
        view.notify(players, "Player " + getPlayerFromDivinity(winner.getWinner()) + " has won");
    }



    private void playTurn(Player player) throws PlayerQuitException, LossException {

        boolean performedAction = false;
        RequestedAction requestedAction;

        do {
            requestedAction = view.performAction(player);
            if (requestedAction.getAction() == Action.QUIT)
                throw new PlayerQuitException(player);
            try {
                performedAction = turnTick.handleTurn(player, requestedAction);
            } catch (LossException e) {
                deletePlayer(player);
                performedAction = true;
            }
            if (performedAction) {

                //TODO cosa è meglio
                view.update(board.getChangedSquares());
                view.notify(player, "Action Successful");
            }
            else
                view.notify(player, "Action Not Successful");

        } while(!(requestedAction.getAction() == Action.ENDTURN && performedAction) && winner.getWinner() == null);
    }

    private void chooseMatchDivinities()
    {
        boolean performedAction = false;
        while(!performedAction)
        {
            try
            {
                chosenDivinities = view.getDivinities(players.get(0),nPlayers);
                performedAction = true;
            }
            catch (Exception e)
            {
                performedAction = false;
            }
        }
    }

    private void choosePlayerDivinity()
    {
        boolean performedAction = false;
        Iterator<Player> playerIterator = players.listIterator(1);
        Player current;
        String currentDivinity;

        while(chosenDivinities.size()>1)
        {
            current = playerIterator.next();
            while(!performedAction) {
                try
                {
                    currentDivinity = view.chooseDivinity(current, chosenDivinities);
                    current.setDivinity(DivinityFactory.create(currentDivinity));
                    chosenDivinities.remove(currentDivinity);
                    performedAction = true;
                }
                catch (Exception e)
                {
                    performedAction = false;
                }
            }
            current.getDivinity().setBoard(board);
        }
        players.get(0).setDivinity(DivinityFactory.create(chosenDivinities.get(0)));
        players.get(0).getDivinity().setBoard(board);
    }

    private void chooseColour(Player player) throws IOException {
        String chosenColour;
        do {
            chosenColour = view.chooseColour(player, colours);
            player.setColour(colours.indexOf(chosenColour));
        }while(colours.remove(chosenColour));
    }

    private void setDivinityMediator() throws IOException {
        divinityMediator = new DivinityMediator();
        for(Player player : players)
        {
            divinityMediator = player.getDivinity().decorate(divinityMediator);
        }
        divinityMediator = new SentinelDecorator(divinityMediator);
        for(Player player : players)
        {
            player.getDivinity().setDivinityMediator(divinityMediator);
            player.getDivinity().setWinner(winner);
        }
    }

    private List<Player> listDeepCopy(List<Player> toCopy)
    {
        List<Player> newList = new ArrayList<>();
        for(Player player : toCopy)
        {
            newList.add(player.clone());
        }
        return newList;
    }

    private void chooseNDivinities()
    {
        boolean performedAction = false;
        Iterator<Player> playerIterator = players.listIterator(1);
        Player current;
        String currentDivinity;

        while(chosenDivinities.size()>1)
        {
            current = playerIterator.next();
            while(!performedAction) {
                try
                {
                    currentDivinity = view.chooseDivinity(current, chosenDivinities);
                    current.setDivinity(DivinityFactory.create(currentDivinity));
                    chosenDivinities.remove(currentDivinity);
                    performedAction = true;
                }
                catch (Exception e)
                {
                    performedAction = false;
                }
            }
            current.getDivinity().setBoard(board);
        }
        players.get(0).setDivinity(DivinityFactory.create(chosenDivinities.get(0)));
        players.get(0).getDivinity().setBoard(board);
    }

    private void placeWorker(int i, Player player) throws IOException {
        Coordinates coordinates;
        boolean performedAction = false;

        while(!performedAction) {
            coordinates = view.getCoordinates(player);

            if(player.getDivinity().placeWorker(player.getWorker(i),coordinates))
            {
                player.getWorker(i).setCoordinates(coordinates);
                performedAction = true;
            }
            else
                view.notify(player,"The cell is invalid");
        }
    }


    private Player getPlayerFromDivinity(Divinity playerDivinity)
    {
        for(Player player : players)
        {
            if(player.getDivinity().getName().equals(playerDivinity.getName()))
                return player;
        }
        return null;
    }

    private void deletePlayer(Player player)
    {
        if(player.getWorker(0)!= null)
            board.getSquare(player.getWorker(0).getCoordinates()).removeTop();
        if(player.getWorker(1)!= null)
            board.getSquare(player.getWorker(1).getCoordinates()).removeTop();
        ((DivinityMediatorDecorator) divinityMediator).removeDecorator(player.getDivinity().getName());
        players.remove(player);
    }

    public void terminateMatch()
    {

    }
}
