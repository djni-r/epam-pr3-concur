/**
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 */
package by.malinouski.uber.client;

import by.malinouski.uber.generator.IdGenerator;
import by.malinouski.uber.location.Location;

/**
 * This is an entity class for Uber client
 * @author makarymalinouski
 */
public class Client extends Thread {

    private long clientId;
    private Location location;
    private Location targetLocation;
    
    public Client() {
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
    
}
