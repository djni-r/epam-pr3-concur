/*
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 */
package by.malinouski.uber.taxi;

import by.malinouski.uber.distance.TimeDistance;
import by.malinouski.uber.generator.IdGenerator;
import by.malinouski.uber.location.Location;
import by.malinouski.uber.taxi.state.AvailableTaxiState;
import by.malinouski.uber.taxi.state.TaxiState;

/**
 * 
 * This is an entity class Taxi which represents a certain taxi
 * which has id, location, and a certain state. 
 * The id is unique and can't be set (for simplicity).
 * Hence each taxi created is unique, 
 * so it does not overrides equals and hashcode 
 *
 * @author makarymalinouski
 */
public class Taxi {

    private long taxiId;
    private TaxiState taxiState;
    private Location location;
    private Location targetLocation;
    private Location finalTargetLocation;
    private TimeDistance totalTimeDistance;
    
    public Taxi() {
        taxiId = IdGenerator.generateTaxiId();
        taxiState = new AvailableTaxiState();
        totalTimeDistance = new TimeDistance(0, 0);
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

    public TimeDistance getTotalTimeDistance() {
        return totalTimeDistance;
    }
    
    public void setTotalTimeDistance(TimeDistance totalTimeDistance) {
        this.totalTimeDistance = totalTimeDistance;
    }
    
    public Location getFinalTargetLocation() {
        return finalTargetLocation;
    }
    
    public void setFinalTargetLocation(Location finalTargetLocation) {
        this.finalTargetLocation = finalTargetLocation;
    }
    
    @Override
    public String toString() {
        return String.format("Taxi: id %s, location %s", taxiId, location);
    }

}
