package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.controller.divinities.*;

public class DivinityFactory {
    public static Divinity create(String DivinityName) {
        switch (DivinityName){
            case "Apollo":
                return new Apollo();
            case "Ares":
                return new Ares();
            case "Athena":
                return new Athena();
            case "Artemis":
                return new Artemis();
            case "Atlas":
                return new Atlas();
            case "Charon":
                return new Charon();
            case "Demeter":
                return new Demeter();
            case "Hephaestus":
                return new Hephaestus();
            case "Hera":
                return new Hera();
            case "Hestia":
                return new Hestia();
            case "Limus":
                return new Limus();
            case "Minotaur":
                return new Minotaur();
            case "Pan":
                return new Pan();
            case "Prometheus":
                return new Prometheus();
            default:
                throw new IllegalStateException("Invalid Divinity: " + DivinityName);
        }
    }
}
