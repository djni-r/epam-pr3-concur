/*
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 */
package by.malinouski.uber.taxi;

import by.malinouski.uber.generator.IdGenerator;
import by.malinouski.uber.location.Location;
import by.malinouski.uber.taxi.state.AvailableTaxiState;
import by.malinouski.uber.taxi.state.TaxiState;

/**
 * 
 * This is an entity class Taxi which represents a certain taxi
 * which has id, location, and a certain state
 *
 * @author makarymalinouski
 */
public class Taxi {

    private long taxiId;
    private TaxiState taxiState;
    private Location location;
    private Location targetLocation;
    
    public Taxi() {
        taxiId = IdGenerator.generateTaxiId();
        taxiState = new AvailableTaxiState();
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
    
    public TaxiState getTaxiState() {
        return taxiState;
    }
    
    public void setTaxiState(TaxiState state) {
        this.taxiState = state;
    }
    
    public long getTaxiId() {
        return taxiId;
    }
    
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("Taxi: id %s, location %s", taxiId, location);
    }
    
}
