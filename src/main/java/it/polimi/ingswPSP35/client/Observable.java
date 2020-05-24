package it.polimi.ingswPSP35.client;

@Deprecated
public interface Observable {
    void notifyObservers();
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
}
