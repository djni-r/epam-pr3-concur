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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.uber.client.Client;
import by.malinouski.uber.exception.EmptyTaxiSetException;
import by.malinouski.uber.exception.NoTaxisAddedException;
import by.malinouski.uber.taxi.Taxi;
import by.malinouski.uber.taxi.TaxiThread;
import by.malinouski.uber.timedistance.BestValue;
import by.malinouski.uber.timedistance.Calculator;

/**
 * Singleton class, which purpose is to mediate between Client and Taxi
 * 
 * @author makarymalinouski
 */
public class Manager {

    static final Logger LOGGER = LogManager.getLogger();
    private static Manager instance = null;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false); 
    private static Lock lock = new ReentrantLock();
    private Condition taxiStateChangeCondition = lock.newCondition();
    private Calculator calculator = new Calculator();
    private Set<Taxi> taxis = new HashSet<>();

    private Manager() {
    }

    public static Manager getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new Manager();
                    instanceCreated.set(true);
                }
            } catch (ExceptionInInitializerError e) {
                LOGGER.fatal("Could not create Manager instance");
                throw new RuntimeException();
            } finally {
                lock.unlock();
            }
        }
        return instance;
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
    
    public Taxi chooseTaxiFor(Client client) throws NoTaxisAddedException {
        lock.lock();
        try {
            LOGGER.debug("In chooseTaxiFor: client location " + client.getLocation());
            
            BestValue bestVal = calculator.calcBestValue(taxis, client);
            
            Taxi taxi = bestVal.getTaxi();
            
            LOGGER.info(String.format(
                    "Client %d got taxi %d.\nTime to arrival %d min\n", 
                    client.getClientId(), 
                    taxi.getTaxiId(),
                    bestVal.getTimeDistance().getMinutes()
                ));
            
            new TaxiThread(taxi, client).start();
            
            // wait till taxi state change before releasing lock for next thread
            try {
                taxiStateChangeCondition.await();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
            
            return taxi;
        } catch(EmptyTaxiSetException e) {
            throw new NoTaxisAddedException();
        } finally {
            LOGGER.debug("UNLOCKING CHOOSETAXIFOR");
            lock.unlock();
        }
        
    }
    
    public void freeCondition() {
        lock.lock();
        try {
            taxiStateChangeCondition.signal();
        } finally {
            lock.unlock();
        }
    }
}
