/*
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 */
package by.malinouski.uber.distance;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.uber.client.Client;
import by.malinouski.uber.location.Location;
import by.malinouski.uber.taxi.Taxi;

/**
 * @author makarymalinouski
 * This class' purpose is Distance calculation
 */
public class Calculator {

    final static Logger LOGGER = LogManager.getLogger();
    private static int SPEED = 1000; // meters/min 
    public Calculator() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Calculates distance between two locations
     */
    public TimeDistance calcTimeDistance(Location loc1, Location loc2) {
        
        double distance = calcDistance(
                loc1.getLatitude(), loc1.getLongitude(),
                loc2.getLatitude(), loc2.getLongitude());
        
        LOGGER.debug("In CalcTimeDistance: distance - " + distance);
        return new TimeDistance((int) distance, SPEED);
    }
    
    /*
     * !! Returns null if taxis is empty
     */
    public Taxi calcBestValue(Collection<Taxi> taxis, Client client) {
        LOGGER.debug("In calcBestValue");
        TimeDistance bestTimeDist = null;
        Taxi bestTaxi = null;
        for (Taxi taxi : taxis) { 
            if (taxi.getTaxiState().isAvailable()) {
            TimeDistance timeDist = calcTimeDistance(taxi.getLocation(), client.getLocation());
            /* OPTIMIZATION: if taxi is not available, see if it's still best
             * with the time left until it will be available
             */
//            if (!taxi.getTaxiState().isAvailable()) {
//                timeDist.setMeters(
//                        calcTimeDistance(taxi.getLocation(), taxi.getTargetLocation()).getMeters() + 
//                        calcTimeDistance(taxi.getTargetLocation(), client.getTargetLocation()).getMeters());
//                timeDist.setMinutes(timeDist.getMinutes() + taxi.getTaxiState().minutesTillEnd());
//            }
            
            if (bestTimeDist == null || timeDist.compareTo(bestTimeDist) < 0) {
                bestTimeDist = timeDist; 
                bestTaxi = taxi; 
            }
            }
        }
        
        return bestTaxi;
    }
    
    // http://www.movable-type.co.uk/scripts/latlong.html
    private double calcDistance(double lat1, double lon1, double lat2, double lon2) {
         double earthR = 6371e3;
         double lat1Radian = Math.toRadians(lat1);
         double lat2Radian = Math.toRadians(lat2);
         double latDiff = Math.toRadians(lat2 - lat1);
         double longDiff = Math.toRadians(lon2 - lon1);
         
         double a = Math.pow(Math.sin(latDiff/2), 2) +
                    Math.cos(lat1Radian) * Math.cos(lat2Radian) *
                    Math.pow(Math.sin(longDiff/2), 2);
         
         double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
         
         double d = earthR * c;
         
         return d;
         
    }
}
