/** 
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 * 
 */
package by.malinouski.uber.manager;

import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.malinouski.uber.client.Client;
import by.malinouski.uber.distance.Calculator;
import by.malinouski.uber.taxi.Driver;
import by.malinouski.uber.taxi.Taxi;

/**
 * Singleton class, which purpose is to mediate between Client and Taxi
 * 
 * @author makarymalinouski
 */
public class Manager {

    private static Manager instance = null;
    private Collection<Taxi> taxis = new ConcurrentSkipListSet<>(); // gotta decide which collection to use
    private Lock lock = new ReentrantLock();

    private Manager() {
    }

    public static Manager getInstance() {
        // TODO
        return instance;
    }
    
    public void addTaxi(Taxi taxi) {
        try {
            lock.lock();
            taxis.add(taxi);
        } finally {
            lock.unlock();
        }
    }
    
    public Taxi chooseTaxiFor(Client client) {
        try {
            lock.lock();
            Calculator calc = new Calculator();
            
            // get the right taxi
            return calc.calcBestValue(taxis, client);
        } finally {
            lock.unlock();
        }
    }
    
    public void sendTaxi(Client client, Taxi taxi) {
        Driver driver = new Driver();
        driver.pickClientUp(client, taxi);
        
        // TODO
        throw new RuntimeException("Not yet implemented");
    }
}
