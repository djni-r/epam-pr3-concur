package by.malinouski.uber.generator;

import java.util.concurrent.ThreadLocalRandom;

import by.malinouski.uber.location.Location;

public class LocationGenerator {

    // Minsk area
    private static double LAT_LOWER_BOUND = 53.80;
    private static double LAT_UPPER_BOUND = 54.00;
    private static double LON_LOWER_BOUND = 27.30;
    private static double LON_UPPER_BOUND = 27.80;
    
    public LocationGenerator() {
    }
    
    public static Location generateLocation() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new Location(random.nextDouble(LAT_LOWER_BOUND, LAT_UPPER_BOUND),
                            random.nextDouble(LON_LOWER_BOUND, LON_UPPER_BOUND));
        
    }

}
