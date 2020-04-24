package it.polimi.ingswPSP35.server.controller;

public class DivinityFactory {
    //TODO implement DivinityFactory
    public Divinity create(String DivinityName) {
        switch (DivinityName){
            case "Athena":
                return new Athena();
            case "Artemis":
                return new Artemis();
            default:
                throw new IllegalStateException("Invalid Divinity: " + DivinityName);
        }
    }
}
