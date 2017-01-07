package by.malinouski.uber.client;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.uber.manager.Manager;
import by.malinouski.uber.taxi.Taxi;
import by.malinouski.uber.taxi.state.RidingTaxiState;

public class ClientThread extends Thread {

    static final Logger LOGGER = LogManager.getLogger(ClientThread.class);
    private static final Lock lock = new ReentrantLock();
    private Manager manager;
    private Client client;
    
    public ClientThread(Client client) {
        super(String.valueOf(client.getClientId()));
        manager = Manager.getInstance();
        this.client = client;
    }

    public void run() {
        LOGGER.info(String.format("Client %s thread started. %s", 
                client.getClientId(), client.getLocation()));
        Taxi taxi = null;
        // do not chooseTaxi until another thread is doing it
        lock.lock();
        try {
            taxi = manager.chooseTaxiFor(client);
        } finally {
            lock.unlock();
        }
        
        while(!taxi.getTaxiState().isReady()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
        
        LOGGER.info(String.format("Got into taxi %d. Riding", taxi.getTaxiId()));
        taxi.setTaxiState(new RidingTaxiState());
    }
}
