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
    }

    private void chooseDivinities() {
        ClientHandler firstClient = clientList.getClientFromPlayer(playerList.get(0));
        firstClient.sendObjectToClient(MessageID.CHOOSEDIVINITIES, allDivinities);
        ArrayList<String> chosenDivinities = (ArrayList<String>) firstClient.getClientInput();

        for (Player player : playerList) {
            ClientHandler client = clientList.getClientFromPlayer(player);
            if (!client.equals(firstClient)) {
                client.sendObjectToClient(MessageID.PICKDIVINITY, chosenDivinities);
                String pickedDivinity = (String) client.getClientInput();
                player.setDivinity(DivinityFactory.create(pickedDivinity));
                chosenDivinities.remove(pickedDivinity);
            }
        }
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

    public void playGame() {}

}
