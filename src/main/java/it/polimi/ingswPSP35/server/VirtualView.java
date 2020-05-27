package it.polimi.ingswPSP35.server;

import it.polimi.ingswPSP35.commons.MessageID;
import it.polimi.ingswPSP35.server.controller.DivinityFactory;
import it.polimi.ingswPSP35.server.model.Coordinates;
import it.polimi.ingswPSP35.server.model.Player;
import it.polimi.ingswPSP35.server.model.Worker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VirtualView {
    ClientList clientList;
    ClientHandler currentClient;
    ArrayList<Player> playerList;

    static List<String> allDivinities = List.of(
            "Apollo",
            "Athena",
            "Artemis",
            "Atlas",
            "Demeter",
            "Hephaestus",
            "Minotaur",
            "Pan",
            "Prometheus");

    public VirtualView(ClientList clientList) {
        this.clientList = clientList;
    }

    public void setup() {
        playerList = new ArrayList<Player>();
        for (ClientHandler clientHandler : clientList) {
            playerList.add(clientHandler.getPlayer());
        }
        playerList.sort(Comparator.comparing(Player::getAge));

        chooseDivinities();
        placeWorkers();

        for (ClientHandler client : clientList)
        {
            client.sendObjectToClient(MessageID.FINISHEDSETUP, null);
        }
    }

    private void chooseDivinities() {
        //ask the first player to choose this game's divinities
        ClientHandler firstClient = clientList.getClientFromPlayer(playerList.get(0));
        if (playerList.size() == 2)
            firstClient.sendObjectToClient(MessageID.CHOOSE2DIVINITIES, allDivinities);
        else if (playerList.size() == 3)
            firstClient.sendObjectToClient(MessageID.CHOOSE3DIVINITIES, allDivinities);
        else throw new IllegalArgumentException();
        ArrayList<String> chosenDivinities = (ArrayList<String>) firstClient.getClientInput();

        //ask players other than the first to pick a Divinity from the reduced list
        for (Player player : playerList) {
            ClientHandler client = clientList.getClientFromPlayer(player);
            if (!client.equals(firstClient)) {
                client.sendObjectToClient(MessageID.PICKDIVINITY, chosenDivinities);
                String pickedDivinity = (String) client.getClientInput();
                player.setDivinity(DivinityFactory.create(pickedDivinity));
                chosenDivinities.remove(pickedDivinity);
            }
        }
        //assign the unselected divinity to the first player
        String lastDivinity = chosenDivinities.get(0);
        playerList.get(0).setDivinity(DivinityFactory.create(lastDivinity));
    }

    private void placeWorkers() {
        for (Player player : playerList) {
            ClientHandler client = clientList.getClientFromPlayer(player);
            int workersPlaced = 0;
            do {
                client.sendObjectToClient(MessageID.PLACEWORKER, null);
                Coordinates chosenSquare = (Coordinates) client.getClientInput();
                if (player.getDivinity().placeWorker(new Worker(chosenSquare, player), chosenSquare)) {
                    workersPlaced++;
                }
                else client.sendNotificationToClient("Invalid square selected, please select a valid square");
            } while (workersPlaced < 2);
        }
    }

    public void playGame() {

    }
}
