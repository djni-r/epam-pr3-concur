/** 
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 * 
 */
package by.malinouski.uber.manager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
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
    private Condition condition = lock.newCondition();
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
    
    public int getTaxisSize() {
        LOGGER.debug(">>>>>>TAXIS SIZE: " + taxis.size());
        return taxis.size();
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
            TaxiThread thread = new TaxiThread(taxi, client);
            thread.start();
            
            LOGGER.info(String.format("Client %d got taxi %d.\nTime to arrival %d min", 
                    client.getClientId(), 
                    taxi.getTaxiId(), 
                    // somewhat lengthy calculation of arrival time
                    taxi.getTaxiState().isAvailable() 
                        ? calculator.calcTimeDistance(
                            taxi.getLocation(), client.getLocation())
                          .getMinutes() 
                        : calculator.addTimeDistances(
                                taxi.getTotalTimeDistance(),
                                calculator.calcTimeDistance(
                                        taxi.getTargetLocation(), client.getLocation()))
                          .getMinutes()
                        ));
                                
            
            // wait till taxi state and target location before unlocking 
            try {
                condition.await();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
//            condition.signal();
            LOGGER.debug("RETURNING TAXI " + taxi.getTaxiId());
            return taxi;
        } finally {
            LOGGER.debug("UNLOCKING CHOOSETAXIFOR");
            lock.unlock();
        }
        
    }
    
    public void freeCondition(Taxi taxi, Client client) {
        try {
            lock.lock();
            // taxi's targetLocation is now the client's location
            taxi.setTargetLocation(client.getTargetLocation());
            taxi.setTaxiState(new ArrivingTaxiState());
            // notify manager that the state is changed
            condition.signal();
            LOGGER.info("Taxi " + taxi.getTaxiId() + " started moving\n");
        } finally {
            lock.unlock();
        }
    }
}
