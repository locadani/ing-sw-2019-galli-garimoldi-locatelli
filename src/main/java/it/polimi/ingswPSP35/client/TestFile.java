package it.polimi.ingswPSP35.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestFile extends Cli{
    List<String> info;
    private final Scanner input;

    public TestFile()
    {
        input = new Scanner(System.in);
        String file = input.nextLine();
        try {
            info = Files.readAllLines(Paths.get("C:\\Users\\Daniele\\IdeaProjects\\ing-sw-2019-galli-garimoldi-locatelli\\src\\main\\java\\it\\polimi\\ingswPSP35\\client\\TestFile\\client"+file+".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getNPlayers() {
        return Integer.parseInt(info.get(0));
    }

    @Override
    public List<String> getDivinities(int numberofplayers) {
        List<String> divinita = new ArrayList<>();
        divinita.add(info.get(3));
        divinita.add(info.get(4));
        return divinita;
    }

    @Override
    public String[] getPlayerInfo() {
        String[] data = {info.get(1), info.get(2)};
        return data;
    }

    @Override
    public String chooseDivinity(List<String> divinitiesList) {
        return info.get(3);
    }

    @Override
    public String chooseColour(List<String> availableColors) {
        return info.get(5);
    }
}
