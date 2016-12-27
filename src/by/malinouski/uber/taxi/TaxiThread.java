/**
 * 
 */
package by.malinouski.uber.taxi;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.uber.client.Client;
import by.malinouski.uber.distance.Calculator;
import by.malinouski.uber.location.Location;
import by.malinouski.uber.taxi.state.ReadyTaxiState;

/**
 * @author makarymalinouski
 *
 */
public class TaxiThread extends Thread {

    static final Logger LOGGER = LogManager.getLogger(); 

    private Taxi taxi;
    private Client client;
    
    public TaxiThread(Taxi taxi, Client client) {
        this.taxi = taxi;
        this.client = client;
    }
    
    public void run() {
        LOGGER.info("Taxi " + taxi.getTaxiId() + " started moving\n");
        move(taxi.getLocation(), client.getLocation());
        taxi.setTaxiState(new ReadyTaxiState());
        LOGGER.info("Ready to pick client up\n");

        move(taxi.getLocation(), client.getTargetLocation());
        LOGGER.info("Brought client to target location\n");
        
        // TODO continue
    }

    private void move(Location from, Location to) {
        int timePassed = 0;
        int timeWillTake = new Calculator().calcTimeDistance(from, to).getMinutes();
        Location deltaLocation = deltaLocation(from, to, timeWillTake);
        double deltaLong = deltaLocation.getLongitude();
        double deltaLat = deltaLocation.getLatitude();
        LOGGER.debug("deltaLong " + deltaLong + "\ndeltaLat " + deltaLat + "\n");
        
        while(!(from).equals(to)) {
            
            /* fake calculation to immitate taxi arrival
             * seconds count as minutes
             */ 
            from.setLongitude(from.getLongitude() + deltaLong);
            from.setLatitude(from.getLatitude() + deltaLat);
            LOGGER.debug(String.format("From location: %s\nTo location %s", 
                    from, to));
            try {
                TimeUnit.SECONDS.sleep(1);
                LOGGER.info(++timePassed + " minutes passed " + taxi.getTaxiState());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                LOGGER.error(e.getMessage());
            }
        }
    }
    
    private Location deltaLocation(Location from, Location to, int timeWillTake) {
        double deltaLong = (to.getLongitude() - from.getLongitude())/timeWillTake;
        double deltaLat = (to.getLatitude() - from.getLatitude())/timeWillTake;
        LOGGER.debug("\n-----------DELTA------------\n" + deltaLong + ", " + deltaLat);
        return new Location(deltaLat, deltaLong);
    }
    
}
