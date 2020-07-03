package it.polimi.ingswPSP35.server.controller;

import it.polimi.ingswPSP35.server.controller.divinities.*;


/**
 * Factory class for all divinities: no divinity should call its constructor explicitly, this class is used for creation
 * of instances of new divinities. <p>
 * This class is only referenced at setup time, but this may change if divinities such as Chaos or Dionysus are implemented.
 *
 * @author Paolo Galli
 */
public class DivinityFactory {

    /**Returns a new instance of the divinity identified by the string {@code DivinityName}. If no such divinity exists, an
     * IllegalStateException is thrown. Note that this is not intended behaviour: if the aforementioned exception is
     * thrown, that means there is an error in the program, as it shouldn't be possible for {@code DivinityName} to not be a
     * valid Divinity.
     *
     * @param DivinityName name of the divinity whose instance is requested by the caller
     * @return a new instance of the desired divinity
     */
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
