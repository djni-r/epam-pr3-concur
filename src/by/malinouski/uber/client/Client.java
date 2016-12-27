/**
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 */
package by.malinouski.uber.client;


import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.uber.generator.IdGenerator;
import by.malinouski.uber.location.Location;
import by.malinouski.uber.manager.Manager;
import by.malinouski.uber.manager.exception.NotSupportedManagerOperation;
import by.malinouski.uber.taxi.Taxi;

/**
 * This is an entity class for Uber client
 * @author makarymalinouski
 */
public class Client extends Thread {

    static final Logger LOGGER = LogManager.getLogger(Client.class);
    private long clientId;
    private Manager manager;
    private Location location;
    private Location targetLocation;
    
    public Client() {
        manager = Manager.getInstance();
        clientId = IdGenerator.generateClientId();
    }
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public Location getTargetLocation() {
        return targetLocation;
    }
    
    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }
    
    
    public long getClientId() {
        return clientId;
    }
    
    
    

    public void run() {
        LOGGER.info("Client thread started");
        Taxi taxi = manager.chooseTaxiFor(this);
        LOGGER.info("Got taxi " + taxi + ". Waiting");
//        while(!taxi.getTaxiState().isAvailable()) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        manager.sendTaxi(this, taxi);
        LOGGER.info("Time and distance left " + manager.checkTime(this, taxi));
        
        while(!taxi.getTaxiState().isReady()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        try {
            manager.pickClientUp(this, taxi);
            LOGGER.info("Got into taxi. Riding");
        } catch (NotSupportedManagerOperation e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
