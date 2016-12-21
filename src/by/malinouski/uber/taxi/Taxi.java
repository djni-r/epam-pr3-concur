/*
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 */
package by.malinouski.uber.taxi;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.malinouski.uber.location.Location;

/**
 * 
 * This is an entity class Taxi which represents a certain taxi
 * which has id, location, and a certain state
 *
 * @author makarymalinouski
 */
public class Taxi {

    private long taxiId;
    private TaxiState state;
    private Location location;
    
    public Taxi() {
        // TODO Auto-generated constructor stub
    }
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public TaxiState getState() {
        return state;
    }
    
    public void setState(TaxiState state) {
        this.state = state;
    }
    
    public long getTaxiId() {
        return taxiId;
    }
    
    public void setTaxiId(long taxiId) {
        this.taxiId = taxiId;
    }
    
}
