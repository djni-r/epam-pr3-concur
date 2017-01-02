/** 
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 * 
 */
package by.malinouski.uber.manager;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.uber.client.Client;
import by.malinouski.uber.distance.Calculator;
import by.malinouski.uber.taxi.Taxi;
import by.malinouski.uber.taxi.TaxiThread;
import by.malinouski.uber.taxi.state.ArrivingTaxiState;

/**
 * Singleton class, which purpose is to mediate between Client and Taxi
 * 
 * @author makarymalinouski
 */
public class Manager {

    static final Logger LOGGER = LogManager.getLogger();
    private static Manager instance = null;
    private static boolean instanceCreated = false; 
    private static Lock lock = new ReentrantLock();
    private Calculator calculator = new Calculator();
    private Set<Taxi> taxis = new HashSet<>();

    private Manager() {
    }

    public static Manager getInstance() {
        if (!instanceCreated) {
            lock.lock();
            try {
                if (!instanceCreated) {
                    instance = new Manager();
                    instanceCreated = true;
                }
            } catch (Exception e) {
                LOGGER.fatal("Could not create Manager instance");
                throw new RuntimeException();
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }
    
    public void addTaxi(Taxi taxi) {
        try {
            lock.lock();
            taxis.add(taxi);
            LOGGER.info("Added taxi: " + taxi);
        } finally {
            lock.unlock();
        }
    }
    
    public Taxi chooseTaxiFor(Client client) {
        try {
            lock.lock();
            LOGGER.debug("In chooseTaxiFor: client location " + client.getLocation());

            Taxi taxi = calculator.calcBestValue(taxis, client);
            
            LOGGER.info(String.format("Client %d got taxi %d.\nTime to arrival %d min", 
                    client.getClientId(), 
                    taxi.getTaxiId(), 
                    calculator.calcTimeDistance(
                            taxi.getLocation(), client.getLocation()).getMinutes()));
            
            
            // taxi's targetLocation is now the client's location
            taxi.setTargetLocation(client.getTargetLocation());
            
            new TaxiThread(taxi, client).start();
            taxi.setTaxiState(new ArrivingTaxiState());
            return taxi;
        } finally {
            lock.unlock();
        }
    }
}
