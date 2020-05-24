package it.polimi.ingswPSP35.client;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

@Deprecated
public class ObservableLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> implements Observable {
    private ArrayList<Observer> observers;

    public ObservableLinkedBlockingQueue() {
        super();
        observers = new ArrayList<Observer>();
    }

    @Override
    public boolean add(E generic) {
        boolean outcome = super.add(generic);
        notifyObservers();
        return outcome;
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
