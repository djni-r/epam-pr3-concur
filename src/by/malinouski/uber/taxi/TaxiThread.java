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
import by.malinouski.uber.location.Location;
import by.malinouski.uber.manager.Manager;
import by.malinouski.uber.taxi.state.ArrivingTaxiState;
import by.malinouski.uber.taxi.state.AvailableTaxiState;
import by.malinouski.uber.taxi.state.ReadyTaxiState;
import by.malinouski.uber.taxi.state.RidingTaxiState;

/**
 * @author makarymalinouski
 * TaxiThread which is used by Manager 
 * after it has chosen a Taxi for a Client. 
 * Thus the thread runs only after it has been ordered,
 * and a Taxi is not running when it hasn't been ordered 
 */
public class TaxiThread extends Thread {

    static final Logger LOGGER = LogManager.getLogger(); 
    private static Manager manager = Manager.getInstance();
    private Taxi taxi;
    private static final Lock lock = new ReentrantLock();//manager.getLock(); 
    private Client client;
    
    public TaxiThread(Taxi taxi, Client client) {
        super(Thread.activeCount() + "-" + String.valueOf(taxi.getTaxiId()));
        this.taxi = taxi;
        this.client = client;
    }
    
    
    public void run() {
        LOGGER.debug("TaxiThread LOCK: " + ((ReentrantLock) lock).isLocked());
        lock.lock();
        LOGGER.debug("TAXI THREAD ENTERED LOCK. Count: " 
                     + ((ReentrantLock) lock).getHoldCount());

        // if taxi is already busy signal Manager.chooseTaxiFor(), it can proceed
        if (!taxi.getTaxiState().isAvailable()) {
//            LOGGER.debug("SIGNALLED manager");
//            manager.getCondition().signal();
            // wait until taxi is available to start towards client
            // right now if there are 4 clients and 1 taxi, three taxithreads enter here
            while (!taxi.getTaxiState().isAvailable()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                }
            }
            // the taxi that was waiting is now startng to arrive
            taxi.setTaxiState(new ArrivingTaxiState());
            lock.unlock();
        } else {
            taxi.setTargetLocation(client.getTargetLocation());
            taxi.setTaxiState(new ArrivingTaxiState());
            // the taxi's state changed, so Manager.chooseTaxiFor() can proceed
//            manager.getCondition().signal();
            lock.unlock();
            LOGGER.debug("TAXI THREAD RELEASING LOCK");
        }
        
        move(taxi.getLocation(), client.getLocation());
        LOGGER.info(String.format("Picking client %d up\n", client.getClientId()));
        taxi.setTaxiState(new ReadyTaxiState());
        
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
        int timeWillTake = new Calculator().calcTimeDistance(from, to).getMinutes();
        LOGGER.debug("TIMEWILLTAKE " + timeWillTake);
        Location deltaLocation = deltaLocation(from, to, timeWillTake != 0 ? timeWillTake : 1);
        double deltaLong = deltaLocation.getLongitude();
        double deltaLat = deltaLocation.getLatitude();
        
        LOGGER.info("Taxi " + taxi.getTaxiId() + " started moving\n");

        while(!(from).equals(to)) {

            from.setLongitude(from.getLongitude() + deltaLong);
            from.setLatitude(from.getLatitude() + deltaLat);
            
            try {
                TimeUnit.SECONDS.sleep(1);
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
