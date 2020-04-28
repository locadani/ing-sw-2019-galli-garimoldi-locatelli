package it.polimi.ingswPSP35.server.controller;

public class DivinityFactory {
    //TODO implement DivinityFactory
    public static Divinity create(String DivinityName) {
        switch (DivinityName){
            case "Athena":
                return new Athena();
            case "Artemis":
                return new Artemis();
            case "Minotaur":
                return new Minotaur();
            case "Prometheus":
                return new Prometheus();
            default:
                throw new IllegalStateException("Invalid Divinity: " + DivinityName);
        }
    }
}
