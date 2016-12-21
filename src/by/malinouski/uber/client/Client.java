/**
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 */
package by.malinouski.uber.client;

import by.malinouski.uber.location.Location;

/**
 * This is an entity class for Uber user
 * @author makarymalinouski
 */
public class Client {

    private ClientState state;
    private Location location;
    /**
     * 
     */
    public Client() {
        // TODO Auto-generated constructor stub
    }
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public ClientState getState() {
        return state;
    }
    
    public void changeState(ClientState state) {
        this.state = state;
    }
    
}
