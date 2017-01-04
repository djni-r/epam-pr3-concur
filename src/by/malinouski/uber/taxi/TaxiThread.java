/**
 * 
 */
package by.malinouski.uber.taxi;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.uber.client.Client;
import by.malinouski.uber.distance.Calculator;
import by.malinouski.uber.distance.TimeDistance;
import by.malinouski.uber.location.Location;
import by.malinouski.uber.manager.Manager;
import by.malinouski.uber.taxi.state.ArrivingTaxiState;
import by.malinouski.uber.taxi.state.AvailableTaxiState;
import by.malinouski.uber.taxi.state.ReadyTaxiState;

/**
 * @author makarymalinouski
 * TaxiThread which is used by Manager 
 * after it has chosen a Taxi for a Client. 
 * Thus the thread runs only after it has been ordered,
 * and a Taxi stays idle when it hasn't been ordered 
 */
public class TaxiThread extends Thread {

    static final Logger LOGGER = LogManager.getLogger(); 
    private static Manager manager = Manager.getInstance();
    private Taxi taxi;
    private final Lock lock = new ReentrantLock(); 
    private Client client;
    
    public TaxiThread(Taxi taxi, Client client) {
        super(String.valueOf(taxi.getTaxiId()));
        this.taxi = taxi;
        this.client = client;
    }
    
    
    public void run() {
        lock.lock();
        while (!taxi.getTaxiState().isAvailable()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
        
        manager.freeCondition(taxi, client);
        LOGGER.debug("TAXI THREAD " + taxi.getTaxiId());
        lock.unlock();
        
        move(taxi.getLocation(), client.getLocation());
        taxi.setTaxiState(new ReadyTaxiState());
        LOGGER.info(String.format("Ready to pick client %d up\n", 
                                  client.getClientId()));
        
        // taxi's targetLocation is now client's targetLocation
//        taxi.setTargetLocation(client.getTargetLocation());
        
        move(taxi.getLocation(), client.getTargetLocation());
        LOGGER.info(String.format("\nBrought client %d to target location\n",
                                    client.getClientId()));
        taxi.setTaxiState(new AvailableTaxiState());
    }

    /* fake calculation to immitate taxi movement
     * change from's Location until from and to are equal 
     * seconds count as minutes
     */
    private void move(Location from, Location to) {
        int timePassed = 0;
        int timeWillTake = new Calculator().calcTimeDistance(from, to).getMinutes();
        LOGGER.debug("TIMEWILLTAKE " + timeWillTake);
        Location deltaLocation = deltaLocation(from, to, timeWillTake != 0 ? timeWillTake : 1);
        double deltaLong = deltaLocation.getLongitude();
        double deltaLat = deltaLocation.getLatitude();
        
        LOGGER.debug("deltaLong " + deltaLong + "\ndeltaLat " + deltaLat + "\n");
        
        while(!(from).equals(to)) {
            
            from.setLongitude(from.getLongitude() + deltaLong);
            from.setLatitude(from.getLatitude() + deltaLat);
            
            LOGGER.debug(String.format("From location: %s\nTo location %s", 
                    from, to));
            try {
                TimeUnit.SECONDS.sleep(1);
                LOGGER.debug(String.format("%d minutes passed %s", 
                                          ++timePassed, taxi.getTaxiState()));
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
    
    /*
     * A private convenience method which represents a change in Location within time
     */
    private Location deltaLocation(Location from, Location to, int timeWillTake) {
        double deltaLong = (to.getLongitude() - from.getLongitude())/timeWillTake;
        double deltaLat = (to.getLatitude() - from.getLatitude())/timeWillTake;
        LOGGER.debug("\n-----------DELTA------------\n" + deltaLong + ", " + deltaLat);
        return new Location(deltaLat, deltaLong);
    }
    
}
