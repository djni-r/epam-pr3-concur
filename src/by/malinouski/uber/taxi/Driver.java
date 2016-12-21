package by.malinouski.uber.taxi;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.malinouski.uber.client.Client;

public class Driver {

    private Lock lock = new ReentrantLock();

    public Driver() {
        // TODO Auto-generated constructor stub
    }
    
    public void pickClientUp(Client user, Taxi taxi) {
        TaxiState state = new TaxiState();
        state.arriving();
        taxi.setState(state);
        
        throw new RuntimeException("Not yet implemented");
    }
}
