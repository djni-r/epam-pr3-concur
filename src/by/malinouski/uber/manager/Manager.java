/** 
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 * 
 */
package by.malinouski.uber.manager;

import java.lang.management.LockInfo;
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
    private Condition taxiStateChangeCondition = lock.newCondition();
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
            } catch (ExceptionInInitializerError e) {
                LOGGER.fatal("Could not create Manager instance");
                throw new RuntimeException();
            } finally {
                lock.unlock();
            }
        }
        LOGGER.debug("RETURNING INSTANCE " + instance.toString());
        return instance;
    }
    
    public Lock getLock() {
        return lock;
    }
    
    public Condition getCondition() {
        return taxiStateChangeCondition;
    }
    public int getTaxisSize() {
        LOGGER.debug("TAXIS SIZE: " + taxis.size());
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
        LOGGER.debug("Manager LOCK is locked: " + ((ReentrantLock) lock).isLocked());
        lock.lock();
        try {
            LOGGER.debug("Manager LOCK: " + ((ReentrantLock) lock).getHoldCount());
            LOGGER.debug("In chooseTaxiFor: client location " + client.getLocation());
            
            Taxi taxi = calculator.calcBestValue(taxis, client);
            
            LOGGER.info(String.format("Client %d got taxi %d.\nTime to arrival %d min", 
                    client.getClientId(), 
                    taxi.getTaxiId(), 
                    // somewhat lengthy calculation of arrival time
                    taxi.getTaxiState().isAvailable()                   // if the taxi was chosen first time
                        ? calculator.calcTimeDistance(                  // calculate timeDistance between taxi
                            taxi.getLocation(), client.getLocation())   // current loc and client's one
                          .getMinutes()
                        : calculator.addTimeDistances(
                                taxi.getTotalTimeDistance(),
                                calculator.calcTimeDistance(
                                        taxi.getFinalTargetLocation(), client.getLocation()))
                          .getMinutes()
                        ));
                                
            TaxiThread thread = new TaxiThread(taxi, client);
            
            thread.start();
            return taxi;
        } finally {
            // wait till taxi state change before releasing lock for next thread
//            try {
//                taxiStateChangeCondition.await();
//            } catch (InterruptedException e) {
//                LOGGER.error(e.getMessage());
//            }
            LOGGER.debug("UNLOCKING CHOOSETAXIFOR");
            lock.unlock();
        }
        
    }
    
    public void freeCondition(Taxi taxi, Client client) {
        lock.lock();
        try {
            // taxi's targetLocation is now the client's location
            taxi.setTargetLocation(client.getTargetLocation());
            taxi.setTaxiState(new ArrivingTaxiState());
            LOGGER.info("Taxi " + taxi.getTaxiId() + " started moving\n");
        } finally {
            lock.unlock();
        }
    }
}
